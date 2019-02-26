variable "api_key" {}
variable "heroku_email" {}
variable "heroku_app_name" {}
variable "aws_secret_key" {}
variable "aws_access_key" {}
variable "dburl" {}

provider "heroku" {
  email   = "${var.heroku_email}"
  api_key = "${var.api_key}"
}

resource "heroku_app" "integration-test" {
  name   = "${var.heroku_app_name}"
  region = "us"

  config_vars {
    port = "8080"
    dburl = "${var.dburl}"
    dbusername = "suinkegzkmwilh"
    dbpassword = "bd78e8b0150ba0c00146cff94fca57e5cbf0799149c42322110237aec363cbcb"
    AWS_ACCESS_KEY = "${var.aws_access_key}"
    AWS_REGION = "us-east-1"
    AWS_SECRET_KEY = "${var.aws_secret_key}"
  }
}