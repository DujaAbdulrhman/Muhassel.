Mohassil is a comprehensive digital platform for managing and collecting taxes in Saudi Arabia. It is designed to streamline invoicing and tax workflows for businesses of all sizes. Core features include invoice generation and confirmation, automatic 15% VAT calculation, exportable tax reports in PDF format, real-time email and WhatsApp notifications, and automatic penalty enforcement for late submissions. The system also delivers analytical insights and calculates annual net profit with precision.


Users: 

 • Admin 
 • Responsible for high-level management and administrative tasks, such as activating taxpayers and adding auditors.
 • Auditor
 • Focuses on regulatory and tax-related tasks, such as creating, approving, and managing tax reports and handling business compliance issues. 
 • Taxpayer
 • Represents business owners or individuals responsible for managing their businesses' financial and tax-related activities, including adding accountants, managing branches, and viewing revenues. 
 • Accountant 
 • Handles day-to-day financial operations for businesses, such as managing sales, restocking products, and operating counter boxes!





Personal endpoints:

1. Add AccountantCreates a new accountant under a taxpayer and business. Sends login credentials to the accountant's email.
2. Create Counter BoxInitializes a new counter box typically used by accountants to manage their daily operations.
3. Open Counter BoxAllows the authenticated accountant to open their assigned counter box at the start of their shift
4.Open Counter BoxAllows the authenticated accountant to open their assigned counter box at the start of their shift
5. Close Counter BoxCloses the accountant’s counter box and calculates the total number of hours it was open
6. Sales Summary by BranchCalculates and returns the total sales amount before tax, the tax amount (15%), and the final total for a specific branch.
 7.Update Product Quantity in SaleAllows an accountant to update the quantity of a product in an unconfirmed sale, with stock validation and price recalculation
8. Get Accountants by Taxpayer IDReturns a structured list of all accountants linked to a specific taxpayer.
9. Delete AccountantDeletes an accountant if they are not assigned to any open counter box or associated data.
1010.Get Tax Report as PDFGenerates a PDF file of a tax report, includes business logo if available, and ensures access control.
 11. Notify Upcoming PaymentsA scheduled background task that sends reminder emails to taxpayers 3 days before a report’s payment due date.







Duja Alsuwaykit
