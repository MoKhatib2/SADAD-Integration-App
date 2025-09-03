BEGIN
    ------------------------------------------------------------
    -- ORGANIZATION
    ------------------------------------------------------------
    INSERT INTO organization (id, code, name) VALUES (1, 'ORG001', 'Global Payments Inc.');
    INSERT INTO organization (id, code, name) VALUES (2, 'ORG002', 'FinTech Solutions');
    INSERT INTO organization (id, code, name) VALUES (3, 'ORG003', 'NextGen Services');
    INSERT INTO organization (id, code, name) VALUES (4, 'ORG004', 'Techno Corp');
    INSERT INTO organization (id, code, name) VALUES (5, 'ORG005', 'Alpha Systems');
	INSERT INTO organization (id, code, name) VALUES (6, 'ORG006', 'Delta Holdings');
    INSERT INTO organization (id, code, name) VALUES (7, 'ORG007', 'Skyline Enterprises');
    INSERT INTO organization (id, code, name) VALUES (8, 'ORG008', 'Bright Future Ltd.');
    INSERT INTO organization (id, code, name) VALUES (9, 'ORG009', 'Innovatech');
    INSERT INTO organization (id, code, name) VALUES (10, 'ORG010', 'Pioneer Corp.');

    ------------------------------------------------------------
    -- BANK
    ------------------------------------------------------------
    INSERT INTO bank (id, code, name) VALUES (1, 'BANK001', 'National Bank');
    INSERT INTO bank (id, code, name) VALUES (2, 'BANK002', 'City Trust Bank');
    INSERT INTO bank (id, code, name) VALUES (3, 'BANK003', 'Capital Finance');
    INSERT INTO bank (id, code, name) VALUES (4, 'BANK004', 'Union Bank');
    INSERT INTO bank (id, code, name) VALUES (5, 'BANK005', 'Prime Bank');
    INSERT INTO bank (id, code, name) VALUES (6, 'BANK006', 'First Regional Bank');
    INSERT INTO bank (id, code, name) VALUES (7, 'BANK007', 'Global Credit Union');
    INSERT INTO bank (id, code, name) VALUES (8, 'BANK008', 'Sunrise Bank');
    INSERT INTO bank (id, code, name) VALUES (9, 'BANK009', 'Heritage Bank');
    INSERT INTO bank (id, code, name) VALUES (10, 'BANK010', 'Pioneer Trust');

    ------------------------------------------------------------
    -- ORGANIZATION_BANK (Join Table)
    ------------------------------------------------------------
    INSERT INTO organization_bank (organization_id, bank_id) VALUES (1, 1);
    INSERT INTO organization_bank (organization_id, bank_id) VALUES (1, 2);
    INSERT INTO organization_bank (organization_id, bank_id) VALUES (2, 3);
    INSERT INTO organization_bank (organization_id, bank_id) VALUES (3, 4);
    INSERT INTO organization_bank (organization_id, bank_id) VALUES (4, 5);
    INSERT INTO organization_bank (organization_id, bank_id) VALUES (5, 1);
    INSERT INTO organization_bank (organization_id, bank_id) VALUES (6, 6);
    INSERT INTO organization_bank (organization_id, bank_id) VALUES (7, 7);
    INSERT INTO organization_bank (organization_id, bank_id) VALUES (8, 8);
    INSERT INTO organization_bank (organization_id, bank_id) VALUES (9, 9);
    INSERT INTO organization_bank (organization_id, bank_id) VALUES (10, 10);

    ------------------------------------------------------------
    -- BANK_ACCOUNT
    ------------------------------------------------------------
    INSERT INTO bank_account (id, account_number, iban, account_name, bank_id, organization_id)
    VALUES (1, '1234567890', 'EG210001234567890', 'Main Account', 1, 1);
    INSERT INTO bank_account (id, account_number, iban, account_name, bank_id, organization_id)
    VALUES (2, '2234567890', 'EG220001234567891', 'Operations Account', 2, 1);
    INSERT INTO bank_account (id, account_number, iban, account_name, bank_id, organization_id)
    VALUES (3, '3234567890', 'EG230001234567892', 'Finance Account', 3, 2);
    INSERT INTO bank_account (id, account_number, iban, account_name, bank_id, organization_id)
    VALUES (4, '4234567890', 'EG240001234567893', 'HR Account', 4, 3);
    INSERT INTO bank_account (id, account_number, iban, account_name, bank_id, organization_id)
    VALUES (5, '5234567890', 'EG250001234567894', 'IT Account', 5, 4);
    INSERT INTO bank_account (id, account_number, iban, account_name, bank_id, organization_id)
    VALUES (6, '6234567890', 'EG260001234567895', 'Payroll Account', 6, 6);
    INSERT INTO bank_account (id, account_number, iban, account_name, bank_id, organization_id)
    VALUES (7, '7234567890', 'EG270001234567896', 'Savings Account', 7, 7);
    INSERT INTO bank_account (id, account_number, iban, account_name, bank_id, organization_id)
    VALUES (8, '8234567890', 'EG280001234567897', 'Reserve Account', 8, 8);
    INSERT INTO bank_account (id, account_number, iban, account_name, bank_id, organization_id)
    VALUES (9, '9234567890', 'EG290001234567898', 'Project Account', 9, 9);
    INSERT INTO bank_account (id, account_number, iban, account_name, bank_id, organization_id)
    VALUES (10, '1034567890', 'EG300001234567899', 'Branch Account', 10, 10);

    ------------------------------------------------------------
    -- LEGAL_ENTITY
    ------------------------------------------------------------
    INSERT INTO legal_entity (id, code, name) VALUES (1, 'LE001', 'Legal Entity A');
    INSERT INTO legal_entity (id, code, name) VALUES (2, 'LE002', 'Legal Entity B');
    INSERT INTO legal_entity (id, code, name) VALUES (3, 'LE003', 'Legal Entity C');
    INSERT INTO legal_entity (id, code, name) VALUES (4, 'LE004', 'Legal Entity D');
    INSERT INTO legal_entity (id, code, name) VALUES (5, 'LE005', 'Legal Entity E');
    INSERT INTO legal_entity (id, code, name) VALUES (6, 'LE006', 'Legal Entity F');
    INSERT INTO legal_entity (id, code, name) VALUES (7, 'LE007', 'Legal Entity G');
    INSERT INTO legal_entity (id, code, name) VALUES (8, 'LE008', 'Legal Entity H');
    INSERT INTO legal_entity (id, code, name) VALUES (9, 'LE009', 'Legal Entity I');
    INSERT INTO legal_entity (id, code, name) VALUES (10, 'LE010', 'Legal Entity J');

    ------------------------------------------------------------
    -- BILLER
    ------------------------------------------------------------
    INSERT INTO biller (id, code, name) VALUES (1, 'BILL001', 'Telecom Egypt');
    INSERT INTO biller (id, code, name) VALUES (2, 'BILL002', 'Vodafone');
    INSERT INTO biller (id, code, name) VALUES (3, 'BILL003', 'Etisalat');
    INSERT INTO biller (id, code, name) VALUES (4, 'BILL004', 'Orange');
    INSERT INTO biller (id, code, name) VALUES (5, 'BILL005', 'Utility Company');
    INSERT INTO biller (id, code, name) VALUES (6, 'BILL006', 'Internet Provider X');
    INSERT INTO biller (id, code, name) VALUES (7, 'BILL007', 'Insurance Company Y');
    INSERT INTO biller (id, code, name) VALUES (8, 'BILL008', 'Water Authority');
    INSERT INTO biller (id, code, name) VALUES (9, 'BILL009', 'Electricity Board');
    INSERT INTO biller (id, code, name) VALUES (10, 'BILL010', 'Gas Utility Corp');

    ------------------------------------------------------------
    -- VENDOR
    ------------------------------------------------------------
    INSERT INTO vendor (id, code, name, biller_id) VALUES (1, 'VEND001', 'Vendor One', 1);
    INSERT INTO vendor (id, code, name, biller_id) VALUES (2, 'VEND002', 'Vendor Two', 2);
    INSERT INTO vendor (id, code, name, biller_id) VALUES (3, 'VEND003', 'Vendor Three', 3);
    INSERT INTO vendor (id, code, name, biller_id) VALUES (4, 'VEND004', 'Vendor Four', 4);
    INSERT INTO vendor (id, code, name, biller_id) VALUES (5, 'VEND005', 'Vendor Five', 5);
    INSERT INTO vendor (id, code, name, biller_id) VALUES (6, 'VEND006', 'Vendor Six', 6);
    INSERT INTO vendor (id, code, name, biller_id) VALUES (7, 'VEND007', 'Vendor Seven', 7);
    INSERT INTO vendor (id, code, name, biller_id) VALUES (8, 'VEND008', 'Vendor Eight', 8);
    INSERT INTO vendor (id, code, name, biller_id) VALUES (9, 'VEND009', 'Vendor Nine', 9);
    INSERT INTO vendor (id, code, name, biller_id) VALUES (10, 'VEND010', 'Vendor Ten', 10);

    ------------------------------------------------------------
    -- VENDOR_SITE
    ------------------------------------------------------------
    INSERT INTO vendor_site (id, code, name, vendor_id) VALUES (1, 'VS001', 'Vendor One - Site A', 1);
    INSERT INTO vendor_site (id, code, name, vendor_id) VALUES (2, 'VS002', 'Vendor One - Site B', 1);
    INSERT INTO vendor_site (id, code, name, vendor_id) VALUES (3, 'VS003', 'Vendor Two - Main Site', 2);
    INSERT INTO vendor_site (id, code, name, vendor_id) VALUES (4, 'VS004', 'Vendor Three - HQ', 3);
    INSERT INTO vendor_site (id, code, name, vendor_id) VALUES (5, 'VS005', 'Vendor Five - Branch', 5);
    INSERT INTO vendor_site (id, code, name, vendor_id) VALUES (6, 'VS006', 'Vendor Six - Office', 6);
    INSERT INTO vendor_site (id, code, name, vendor_id) VALUES (7, 'VS007', 'Vendor Seven - Factory', 7);
    INSERT INTO vendor_site (id, code, name, vendor_id) VALUES (8, 'VS008', 'Vendor Eight - Branch', 8);
    INSERT INTO vendor_site (id, code, name, vendor_id) VALUES (9, 'VS009', 'Vendor Nine - Depot', 9);
    INSERT INTO vendor_site (id, code, name, vendor_id) VALUES (10, 'VS010', 'Vendor Ten - HQ', 10);

    ------------------------------------------------------------
    -- BUSINESS
    ------------------------------------------------------------
    INSERT INTO business (id, code, name) VALUES (1, 'BUS001', 'Retail Business');
    INSERT INTO business (id, code, name) VALUES (2, 'BUS002', 'Wholesale Business');
    INSERT INTO business (id, code, name) VALUES (3, 'BUS003', 'Online Business');
    INSERT INTO business (id, code, name) VALUES (4, 'BUS004', 'Consulting Business');
    INSERT INTO business (id, code, name) VALUES (5, 'BUS005', 'Logistics Business');
    INSERT INTO business (id, code, name) VALUES (6, 'BUS006', 'Manufacturing Business');
    INSERT INTO business (id, code, name) VALUES (7, 'BUS007', 'Healthcare Business');
    INSERT INTO business (id, code, name) VALUES (8, 'BUS008', 'Education Business');
    INSERT INTO business (id, code, name) VALUES (9, 'BUS009', 'Hospitality Business');
    INSERT INTO business (id, code, name) VALUES (10, 'BUS010', 'Transport Business');

    ------------------------------------------------------------
    -- EXPENSE_ACCOUNT
    ------------------------------------------------------------
    INSERT INTO expense_account (id, code, name) VALUES (1, 'EXP001', 'Travel Expenses');
    INSERT INTO expense_account (id, code, name) VALUES (2, 'EXP002', 'Office Supplies');
    INSERT INTO expense_account (id, code, name) VALUES (3, 'EXP003', 'Software Licenses');
    INSERT INTO expense_account (id, code, name) VALUES (4, 'EXP004', 'Marketing Expenses');
    INSERT INTO expense_account (id, code, name) VALUES (5, 'EXP005', 'Maintenance Expenses');
    INSERT INTO expense_account (id, code, name) VALUES (6, 'EXP006', 'Training Expenses');
    INSERT INTO expense_account (id, code, name) VALUES (7, 'EXP007', 'Legal Fees');
    INSERT INTO expense_account (id, code, name) VALUES (8, 'EXP008', 'Insurance Premiums');
    INSERT INTO expense_account (id, code, name) VALUES (9, 'EXP009', 'Consultancy Fees');
    INSERT INTO expense_account (id, code, name) VALUES (10, 'EXP010', 'Utilities Expenses');

    ------------------------------------------------------------
    -- INVOICE_TYPE
    ------------------------------------------------------------
    INSERT INTO invoice_type (id, name) VALUES (1, 'Standard Invoice');
    INSERT INTO invoice_type (id, name) VALUES (2, 'Credit Memo');
    INSERT INTO invoice_type (id, name) VALUES (3, 'Debit Memo');
    INSERT INTO invoice_type (id, name) VALUES (4, 'Proforma Invoice');
    INSERT INTO invoice_type (id, name) VALUES (5, 'Recurring Invoice');
    INSERT INTO invoice_type (id, name) VALUES (6, 'Advance Invoice');
    INSERT INTO invoice_type (id, name) VALUES (7, 'Final Invoice');
    INSERT INTO invoice_type (id, name) VALUES (8, 'Timesheet Invoice');
    INSERT INTO invoice_type (id, name) VALUES (9, 'Milestone Invoice');
    INSERT INTO invoice_type (id, name) VALUES (10, 'Interim Invoice');

    ------------------------------------------------------------
    -- LOCATION
    ------------------------------------------------------------
    INSERT INTO location (id, code, name) VALUES (1, 'LOC001', 'Cairo HQ');
    INSERT INTO location (id, code, name) VALUES (2, 'LOC002', 'Alexandria Branch');
    INSERT INTO location (id, code, name) VALUES (3, 'LOC003', 'Giza Office');
    INSERT INTO location (id, code, name) VALUES (4, 'LOC004', 'Mansoura Office');
    INSERT INTO location (id, code, name) VALUES (5, 'LOC005', 'Aswan Branch');
    INSERT INTO location (id, code, name) VALUES (6, 'LOC006', 'Tanta Branch');
    INSERT INTO location (id, code, name) VALUES (7, 'LOC007', 'Suez Office');
    INSERT INTO location (id, code, name) VALUES (8, 'LOC008', 'Port Said Branch');
    INSERT INTO location (id, code, name) VALUES (9, 'LOC009', 'Luxor Branch');
    INSERT INTO location (id, code, name) VALUES (10, 'LOC010', 'Ismailia Office');

    ------------------------------------------------------------
    -- COST_CENTER
    ------------------------------------------------------------
    INSERT INTO cost_center (id, code, description) VALUES (1, 'CC001', 'Administration');
    INSERT INTO cost_center (id, code, description) VALUES (2, 'CC002', 'Human Resources');
    INSERT INTO cost_center (id, code, description) VALUES (3, 'CC003', 'Research and Development');
    INSERT INTO cost_center (id, code, description) VALUES (4, 'CC004', 'Customer Service');
    INSERT INTO cost_center (id, code, description) VALUES (5, 'CC005', 'Sales and Marketing');
    INSERT INTO cost_center (id, code, description) VALUES (6, 'CC006', 'Information Technology');
    INSERT INTO cost_center (id, code, description) VALUES (7, 'CC007', 'Finance Department');
    INSERT INTO cost_center (id, code, description) VALUES (8, 'CC008', 'Operations Management');
    INSERT INTO cost_center (id, code, description) VALUES (9, 'CC009', 'Quality Assurance');
    INSERT INTO cost_center (id, code, description) VALUES (10, 'CC010', 'Supply Chain');
END;

COMMIT;
