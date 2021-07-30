**#Account Statement Module**

App Start Path :

src/main/java/com/nagarro/account/NagarroAccountAppApplication

DB location :

src/main/resources/hibernate.cfg.xml -> hibernate.connection.url 


NOTE :

As mentioned default with no params specified, LAST three months (From Today) of statement is fetched.
(assuming there are fresh transactions else it returns "No Data/Match")
