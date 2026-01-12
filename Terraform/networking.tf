module "vpc" {
  source  = "terraform-aws-modules/vpc/aws"
  version = "6.5.1"

  name = "eks-vpc"
  cidr = "192.168.0.0/16"

  azs = ["us-east-1a", "us-east-1b", "us-east-1c"]

  public_subnets  = ["192.168.101.0/24", "192.168.102.0/24", "192.168.103.0/24"]
  private_subnets = ["192.168.1.0/24", "192.168.2.0/24", "192.168.3.0/24"]
  #En Produccion se deben de crear un NatGateway por cada AZ para evitar
  #tener un solo punto de Fallo
  enable_nat_gateway = true
  single_nat_gateway = true
  #Necesario Internet Gateway para que podamos acceder a internet
  create_igw = true
  # Tags required by Kubernetes AWS Cloud Controller Manager
  # Enable DNS hostnames and support for load balancers in the public subnets
  enable_dns_hostnames = true
  # Enable auto-assignment of public IPv4 addresses for instances in public subnets
  map_public_ip_on_launch = true

  #Crucial tagss for EKS and Load Balancer discovery
  public_subnet_tags = {
    "kubernetes.io/cluster/eks-app" = "shared"
    "kubernetes.io/role/elb"        = "1"
  }

  private_subnet_tags = {
    "kubernetes.io/cluster/eks-app"   = "shared"
    "kubernetes.io/role/internal-elb" = "1"
  }
  tags = {
    Terraform   = "true"
    Environment = "dev"
  }

}

module "efs-sg" {
  source              = "terraform-aws-modules/security-group/aws"
  name                = "NFS Rule"
  description         = "NFS Rule"
  vpc_id              = module.vpc.vpc_id
  ingress_cidr_blocks = ["192.168.0.0/16"]
  ingress_rules       = ["nfs-tcp"]
  egress_cidr_blocks  = ["192.168.0.0/16"]
  egress_rules        = ["all-all"]
}