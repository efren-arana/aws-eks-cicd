# variables.tf

variable "aws_region" {
  description = "AWS region for the EFS filesystem"
  type        = string
  default     = "us-east-1" # alterar
}

variable "efs_name" {
  description = "Name of the EFS filesystem"
  type        = string
  default     = "postgres-efs" # alterar
}
