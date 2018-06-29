package today.urself.redis

import io.lettuce.core.{RedisURI, RedisClient => RC}
import cats._
import cats.syntax._
import cats.data._
import cats.effect._
import io.lettuce.core.api.StatefulRedisConnection
import fs2._
import fs2.interop.reactivestreams._

import scala.concurrent.ExecutionContext

object RedisClient {
  def apply[F[_]]()(implicit F: Effect[F], ec: ExecutionContext): F[LettuceRedisClient[F]] =
    F.delay(new LettuceRedisClient[F](RC.create()))
}

class LettuceRedisClient[F[_]](client: RC)(implicit F: Effect[F], ec: ExecutionContext) {
  def acquire(uri: RedisURI): F[LettuceRedisConnection[F]] =
    F.delay(new LettuceRedisConnection(client.connect(uri)))
  def release(c: LettuceRedisConnection[F]): F[Unit] = F.delay(c.client.close())
}

class LettuceRedisConnection[F[_]](
    val client: StatefulRedisConnection[String, String])(
    implicit F: Effect[F], ec: ExecutionContext) {
  def sadd(key: String, values: List[String]): Stream[F, java.lang.Long] =
    fromPublisher[F, java.lang.Long](client.reactive().sadd(key, values: _*))
  def smembers(key: String): Stream[F, String] =
    fromPublisher[F, String](client.reactive().smembers(key))
  def set(key: String, value: String): Stream[F, String] =
    fromPublisher[F, String](client.reactive().set(key, value))
  def get(key: String): Stream[F, String] =
    fromPublisher[F, String](client.reactive().get(key))
  def del(keys: List[String]): Stream[F, java.lang.Long] =
    fromPublisher[F, java.lang.Long](client.reactive().del(keys:_*))

}
