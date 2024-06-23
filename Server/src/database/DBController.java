package database;

import java.io.IOException;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ocsf.server.ConnectionToClient;

public class DBController {
    private static Connection conn = null;
    // connection to data base
    public static void ConnectToDB(String url,String username,String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            System.out.println("Driver definition succeeded");
        } catch (Exception ex) {
            System.out.println("Driver definition failed");
            ex.printStackTrace();
        }

        try {
            
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("SQL connection succeeded");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            ex.printStackTrace();
        }
    }

    //check if the user exist in database this method for login page
    public static boolean checkUserCredentials(String username, String password, String role) throws SQLException {
        boolean userExists = false;

        String query = "SELECT * FROM "+role+" WHERE username = ? AND password = ? ";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    userExists = true;
                }
            }
        }

        return userExists;
    }

    //close connection of data base
    public static void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
            System.out.println("SQL connection closed.");
        }
    }
    
    
 // save data in the database
    public static void saveData(String sql, Object[] params,ConnectionToClient client) {
        try {
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(sql);
                // Set the parameter values
                for (int i = 0; i < params.length; i++) {
                    statement.setObject(i + 1, params[i]);
                }

                // Execute the SQL statement
                statement.executeUpdate();

                System.out.println("Data saved successfully!");
                try {
					client.sendToClient("SqlOperationSuccss");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            } else {
                System.out.println("Connection is not established.");
                try {
					client.sendToClient("SqlOperationfail");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Rest of your code...

    
    //delete data from data base
    public static void deleteData(String sql, Object[] params,ConnectionToClient client) {
        try {
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(sql);
                // Set the parameter values
                for (int i = 0; i < params.length; i++) {
                    statement.setObject(i + 1, params[i]);
                }

                // Execute the SQL statement
                statement.executeUpdate();

                System.out.println("Data deleted successfully!");
                try {
					client.sendToClient("SqlOperationSuccss");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            } else {
                System.out.println("Connection is not established.");
                try {
					client.sendToClient("SqlOperationfail");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
   
    
    //get data from data base
    public static void getdata(String sql, Object[] params, ConnectionToClient client) {
        List<Map<String, Object>> resultList = new ArrayList<>();

        try {
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(sql);
                // Set the parameter values
                for (int i = 0; i < params.length; i++) {
                    statement.setObject(i + 1, params[i]);
                }

                // Execute the SQL statement
                ResultSet resultSet = statement.executeQuery();

                // Retrieve column names
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                List<String> columnNames = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    columnNames.add(metaData.getColumnName(i));
                }

                // Retrieve data rows
                while (resultSet.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (String columnName : columnNames) {
                        row.put(columnName, resultSet.getObject(columnName));
                    }
                    resultList.add(row);
                }

                resultSet.close();
                statement.close();
            } else {
                System.out.println("Connection is not established.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            client.sendToClient(resultList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    // edit existing data 
    public static void editData(String sql, Object[] params,ConnectionToClient client) {
        try {
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(sql);
                // Set the parameter values
                for (int i = 0; i < params.length; i++) {
                    statement.setObject(i + 1, params[i]);
                }

                // Execute the SQL statement
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Data edited successfully!");
                    try {
						client.sendToClient("SqlOperationSuccss");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                } else {
                    System.out.println("No data was edited.");
                    try {
						client.sendToClient("SqlOperationfail");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            } else {
                System.out.println("Connection is not established.");
                try {
					client.sendToClient("SqlOperationfail");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //check if the data in the data base or not
    public static void checkDataExists(String sql, Object[] params, ConnectionToClient client) {
        try {
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(sql);

                // Set the parameter values
                for (int i = 0; i < params.length; i++) {
                    statement.setObject(i + 1, params[i]);
                }

                // Execute the SQL statement
                ResultSet rs = statement.executeQuery();

                // Check if data exists
                if (rs.next()) {
                    try {
                        client.sendToClient("DataExist");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }  
                } else {
                    try {
                        client.sendToClient("DataNotExist");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                rs.close();
                statement.close();
            } else {
                System.out.println("Connection is not established.");
                try {
                    client.sendToClient("DataNotExist");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Map<String, Object>> getDataFromMySQL(String sql, Object[] params) {
        List<Map<String, Object>> resultList = new ArrayList<>();

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            // Set the parameter values
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }

            // Execute the SQL statement
            try (ResultSet resultSet = statement.executeQuery()) {
                // Retrieve column names
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                List<String> columnNames = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    columnNames.add(metaData.getColumnName(i));
                }

                // Retrieve data rows
                while (resultSet.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (String columnName : columnNames) {
                        row.put(columnName, resultSet.getObject(columnName));
                    }
                    resultList.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }
    
    
    public boolean InsertCheckingCopies(String sql, Object[] params) {
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            // Set the parameter values
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            
            // Execute the SQL statement
            int rowsAffected = statement.executeUpdate();

            // Check if any rows were affected
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    private void closeResources(ResultSet rs, PreparedStatement stmt) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public int[] getrangresult(String sqlQuery) {
        try (PreparedStatement stmt = conn.prepareStatement(sqlQuery);
             ResultSet rs = stmt.executeQuery()) {
            // Determine the number of rows in the result set
            rs.last();
            int numRows = rs.getRow();
            rs.beforeFirst();
            // Create an array to store the result
            int[] result = new int[numRows];
            // Retrieve the values from the result set and store them in the array
            int index = 0;
            while (rs.next()) {
                result[index++] = rs.getInt(1); // Assuming the result contains a single column of integers
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new int[0]; // Return an empty array if an error occurs
    }
    
}
