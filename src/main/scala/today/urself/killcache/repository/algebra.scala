package today.urself.killcache.repository

import today.urself.killcache.model.{User, UserName}

object algebra {

  trait UserRepository[F[_]] {
    def findUser(username: UserName): F[Option[User]]
  }

}
