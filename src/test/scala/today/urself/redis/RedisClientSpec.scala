package today.urself.redis

import java.util.concurrent.TimeUnit

import ai.grakn.redismock.RedisServer
import io.lettuce.core.RedisURI
import cats._
import cats.syntax._
import cats.implicits._
import cats.effect._
import fs2._
import org.scalatest.{
  BeforeAndAfterAll,
  BeforeAndAfterEach,
  FlatSpec,
  MustMatchers
}

class RedisClientSpec
    extends FlatSpec
    with MustMatchers
    with BeforeAndAfterEach
    with BeforeAndAfterAll {

  var redis: RedisServer = null

  override def beforeAll(): Unit = {}

  override def afterAll(): Unit = {}

  override def beforeEach(): Unit = {}

  import scala.concurrent.ExecutionContext.Implicits.global

  "RedisClient" should "be able to set and get a key" in {
    val localRedis = RedisURI.create("localhost", 6379)
    RedisClient[IO]()
      .flatMap { c =>
        for {
          con <- c.acquire(localRedis)
          r <- {
            for {
              _ <- con.del(List("a"))
              s <- con.set("a", "test value")
              r <- con.get("a")
              d <- con.del(List("a"))
            } yield (s, r, d)
          }.compile.toVector.map(_.headOption)
          _ <- c.release(con)

        } yield r
      }
      .unsafeRunSync() must equal(Some(("OK", "test value", 1L)))
  }

  "RedisClient" should "be able to add things to a set then retrieve the whole set" in {
    val localRedis = RedisURI.create("localhost", 6379)
    RedisClient[IO]()
      .flatMap { c =>
        for {
          con <- c.acquire(localRedis)
          r <- {
            for {
              _ <- con.del(List("testset"))
              _ <- con.sadd("testset", ('a' to 'z').toList.map(_.toString))
              _ <- con.sadd("testset", List("A"))
              r <- con.smembers("testset")
              d <- con.del(List("testset"))
            } yield r
          }.compile.toVector
          _ <- c.release(con)

        } yield r
      }
      .unsafeRunSync() must equal(('a' to 'z').toList.map(_.toString))
  }

}
