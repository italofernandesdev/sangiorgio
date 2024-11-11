# Instructions

### Database

* The DB credentials should be stored as environment variables called:
    * DB_USER
    * DB_PASSWORD

To start using the application, is needed to add some data to the database.

* Example of adding a Billing to the database (At least one Billing is required)
```sql
INSERT INTO `sangiorgio`.`billing` (`code`, `original_amount`) VALUES("BILLING01", 1000.00);
```
* Example of adding a Seller to the database (At least one Seller is required)
```sql
INSERT INTO `sangiorgio`.`seller` (`code`) VALUES("VEND01");
```

### AWS Connection
* The AWS credentials should be stored as environment variables called:
  * AWS_ACCESS_KEY
  * AWS_SECRET_KEY

