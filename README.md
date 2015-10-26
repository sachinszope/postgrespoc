# postgrespoc
Postgres POC using JDBC, JDBI and JOOQ

<h2>APIs Exposed</h2>
<h4>Create User (POST)</h4>
    postgrespoc/gooru/jdbc-postgres
    postgrespoc/gooru/jdbi-postgres
    postgrespoc/gooru/jooq-postgres

<strong>Sample Request Payload:</strong><br>
{
  "profilePic": "/user/67",
  "collectionsCreated": 12,
  "resourceAdded": 22,
  "assessmentAdded": 32,
  "displayName": "ouwioruqweir",
  "fullName": "iqowurioqweu o qwerqwer"
}

<h4>Get User (GET)</h4>
    postgrespoc/gooru/jdbc-postgres/<ID>
    postgrespoc/gooru/jdbi-postgres/<ID>
    postgrespoc/gooru/jooq-postgres/<ID>

    Path Param:
      ID: User Id to be fetched

<h4>Get Attributes as JSON</h4>
    postgrespoc/gooru/jdbc-postgres/json/<ID>
    postgrespoc/gooru/jdbi-postgres/json/<ID>
    postgrespoc/gooru/jooq-postgres/json/<ID>

    Path Param:
      ID: User Id of which atttributes to be fetched

<h4>Get Attributes as Text</h4>
    postgrespoc/gooru/jdbc-postgres/text/<ID>
    postgrespoc/gooru/jdbi-postgres/text/<ID>
    postgrespoc/gooru/jooq-postgres/text/<ID>

    Path Param:
      ID: User Id of which atttributes to be fetched

<h2>Table Structure</h2>

     Column           |         Type          |                        Modifiers                        
    ------------------+-----------------------+---------------------------------------------------------
      id              | integer               | not null default nextval('gooru_user_id_seq'::regclass)
      user_name       | character varying(50) | not null
      attributes      | json                  | 
      attributes_text | character varying     | 
      
    Indexes:
      "gooru_user_pkey" PRIMARY KEY, btree (id)
