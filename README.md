# SqlServer Reporting automation

## data origin

+ File .CSV
+ Database sqlserver
+ reporting service   <-- por definir

## scenarios

````Gherkin

````

> https://picocli.info/#_introduction  
> https://github.com/phola/SSRS-Docker/blob/master/Dockerfile

 ````java
class Geeks {
    public static void main(String[] args) throws Exception {
        String url
                = "jdbc:mysql://localhost:3306/table_name"; // table details
        String username = "rootgfg"; // MySQL credentials
        String password = "gfg123";
        String query
                = "select * from students"; // query to be run
        Class.forName(
                "com.mysql.cj.jdbc.Driver"); // Driver name
        Connection con = DriverManager.getConnection(
                url, username, password);
        System.out.println(
                "Connection Established successfully");
        Statement st = con.createStatement();
        ResultSet rs
                = st.executeQuery(query); // Execute query
        rs.next();
        String name
                = rs.getString("name"); // Retrieve name from db

        System.out.println(name); // Print result on console
        st.close(); // close statement
        con.close(); // close connection
        System.out.println("Connection Closed....");
    }
}
```` 
