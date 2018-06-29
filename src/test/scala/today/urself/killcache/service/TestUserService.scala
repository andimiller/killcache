package today.urself.killcache.service

import cats.effect.IO
import today.urself.killcache.TestUsers.users
import today.urself.killcache.model.UserName
import today.urself.killcache.repository.algebra.UserRepository

object TestUserService {

  private val testUserRepo: UserRepository[IO] =
    (username: UserName) => IO {
      users.find(_.username.value == username.value)
    }

  val service: UserService[IO] = new UserService[IO](testUserRepo)

}
