package sylius.settings

trait Settings {
  def load(): LoadSettings
  def project(): ProjectSettings
}