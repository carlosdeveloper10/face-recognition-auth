provider "heroku" {
  email   = "carlos.montano.19@outlook.com"
  api_key = "94a8686d-7c2e-4f98-82e9-4b1b00e10e11"
}

variable "heroku_app_name" {}

resource "heroku_app" "integration" {
  name   = "${var.heroku_app_name}"
  region = "us"

  config_vars {
    port = "8080"
    dburl = "jdbc:postgresql://ec2-174-129-224-157.compute-1.amazonaws.com:5432/dbd47u60jcgd3o"
    dbusername = "suinkegzkmwilh"
    dbpassword = "bd78e8b0150ba0c00146cff94fca57e5cbf0799149c42322110237aec363cbcb"
    AWS_ACCESS_KEY = "AKIAJ6FTA2YRTKBCCLXA"
    AWS_REGION = "us-east-1"
    AWS_SECRET_KEY = "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"
  }
}