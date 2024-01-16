package com.onikai.backend.model.enity

import com.onikai.backend.model.enum.Role
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.Instant


class UserPrincipal(
  private val entity:User
) : UserDetails {

  override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf(SimpleGrantedAuthority(entity.role.name))
  override fun getPassword(): String = entity.password!!

  override fun getUsername(): String = entity.username!!

  override fun isAccountNonExpired(): Boolean = true

  override fun isAccountNonLocked(): Boolean = true

  override fun isCredentialsNonExpired(): Boolean = true

  override fun isEnabled(): Boolean = true

  val id:Long?
    get() = entity.id!!
}

@Entity
@Table(name = "users")
open class User  {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
  @SequenceGenerator(name = "users_id_seq", allocationSize = 1)
  var id: Long? = null

  @Column
  var email:String? = null

  @Column(name = "username")
  var username:String? = null

  @Column(name = "password")
  var password:String? = null

  @Enumerated(EnumType.STRING)
  var role: Role = Role.USER;

  @Column var unconfirmedEmail:String? = null

  @Column var resetPasswordToken:String? = null
  @Column var resetPasswordSentAt:Instant? = null

  @Column var confirmationToken:String? = null
  @Column var confirmationSentAt:Instant? = null
  @Column var confirmedAt:Instant? = null

  @Column var createdAt:Instant? = null
  @Column var updatedAt:Instant? = null

  @Column(name = "elo_rating")
  var eloRating: Int? = null

  @NotNull
  @Column(name = "games_won", nullable = false)
  var gamesWon: Int? = null

  @NotNull
  @Column(name = "games_played", nullable = false)
  var gamesPlayed: Int? = null

  @NotNull
  @Column(name = "is_guest", nullable = false)
  var isGuest: Boolean? = false
}

