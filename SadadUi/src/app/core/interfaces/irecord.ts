export interface irecord {
  id: number;
  organization: CodeName;
  legalEntity: CodeName;
  remitterBank: CodeName;
  remitterBankAccount: RemitterBankAccount;
  biller: CodeName;
  vendor: CodeName;
  vendorSite: CodeName;
  invoiceType: CodeName;
  invoiceNumber: string;
  subscriptionAccountNumber: string;
  amount: number;
  expenseAccount: CodeName;
  business: CodeName;
  location: CodeName;
  costCenters: CostCenter[];
  status: string;
}

interface CostCenter {
  id: number;
  code: string;
  description: string;
  percentage: number;
}

interface RemitterBankAccount {
  id: number;
  accountNumber: string;
  accountName: string;
}

interface CodeName {
  id: number;
  code: string;
  name: string;
}