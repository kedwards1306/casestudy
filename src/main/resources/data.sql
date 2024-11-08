INSERT INTO Vendor (Address1,City,Province,PostalCode,Phone,Type,Name,Email)VALUES ('123 Maple
St','London','On', 'N1N-1N1','(555)555-5555','Trusted','ABC Supply Co.','abc@supply.com');
INSERT INTO Vendor (Address1,City,Province,PostalCode,Phone,Type,Name,Email) VALUES ('543
Sycamore Ave','Toronto','On', 'N1P-1N1','(999)555-5555','Trusted','Big Bills
Depot','bb@depot.com');
INSERT INTO Vendor (Address1,City,Province,PostalCode,Phone,Type,Name,Email) VALUES ('922 Oak
St','London','On', 'N1N-1N1','(555)555-5599','Untrusted','Shady Sams','ss@underthetable.com');
INSERT INTO Vendor (Address1,City,Province,PostalCode,Phone,Type,Name,Email) VALUES ('828 Freedom
St','London','On', 'N1F-1N1','(555)555-5500','Untrusted','Keith Edwards','ke@mart.com');
--Adding some products to the table
INSERT INTO Product (ID, VendorID, Name, CostPrice, MSRP, ROP, EOQ, QOH, QOO, QRCode, QRCodeTxt)
Values ('Boo', 1, 'Paper 90', 0.1, 0.1, 100, 100, 100, 100, NULL, NULL);

INSERT INTO Product (ID, VendorID, Name, CostPrice, MSRP, ROP, EOQ, QOH, QOO, QRCode, QRCodeTxt)
Values ('KE345', 1, 'Tennis', 0.12, 0.12, 50, 50, 70, 70, NULL, NULL);

INSERT INTO Product (ID, VendorID, Name, CostPrice, MSRP, ROP, EOQ, QOH, QOO, QRCode, QRCodeTxt)
Values ('KE56', 1, 'Games', 0.15, 0.15, 40, 40, 40, 40, NULL, NULL);

INSERT INTO Product (ID, VendorID, Name, CostPrice, MSRP, ROP, EOQ, QOH, QOO, QRCode, QRCodeTxt)
Values ('E890', 1, 'Computer', 0.4, 0.4, 120, 120, 120, 120, NULL, NULL);
