module "efs" {
  source = "terraform-aws-modules/efs/aws"

  # File system
  name           = var.efs_name
  creation_token = "example-token"
  #Si requiere encriptar el EFS descomentar esta linea y agregar el kms_key_id
  #encrypted      = true
  #  kms_key_arn    = "arn:aws:kms:eu-west-1:111122223333:key/1234abcd-12ab-34cd-56ef-1234567890ab"

  performance_mode = "generalPurpose"
  throughput_mode  = "elastic"
  #  provisioned_throughput_in_mibps = 256

  # Mount targets
  mount_targets = {
    "us-east-1a" = {
      subnet_id = module.vpc.private_subnets[0]
    }
    "us-east-1b" = {
      subnet_id = module.vpc.private_subnets[1]
    }
    "us-east-1c" = {
      subnet_id = module.vpc.private_subnets[2]
    }
  }

  # Security group for EFS
  security_group_description = "EFS security group"
  security_group_vpc_id      = module.vpc.vpc_id
  security_group_ingress_rules = {
    vpc_1 = {
      # relying on the defaults provided for EFS/NFS (2049/TCP + ingress)
      description = "NFS ingress from VPC private subnets"
      cidr_ipv4   = "192.168.1.0/24"
    }
    vpc_2 = {
      # relying on the defaults provided for EFS/NFS (2049/TCP + ingress)
      description = "NFS ingress from VPC private subnets"
      cidr_ipv4   = "192.168.2.0/24"
    }
    vpc_3 = {
      # relying on the defaults provided for EFS/NFS (2049/TCP + ingress)
      description = "NFS ingress from VPC private subnets"
      cidr_ipv4   = "192.168.3.0/24"
    }
  }
  # Backup policy
  enable_backup_policy = false

  tags = {
    Terraform   = "true"
    Environment = "dev"
    Author      = "Efren Arana"
  }
}