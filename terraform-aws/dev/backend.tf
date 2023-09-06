terraform {
 backend "s3" {
   bucket = "dx-test-bucket-1"
   key = "~/.aws/config"
   region = "ap-southeast-2"
 }
}