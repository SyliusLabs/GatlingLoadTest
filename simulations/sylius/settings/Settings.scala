package sylius.settings

trait Settings {
  def load(): LoadSettings
  def project(): ProjectSettings
  def gatling(): GatlingSettings
}