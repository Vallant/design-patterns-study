public abstract class DBManager implements RepositoryFactory
{
  private static final String DRIVER_POSTGRES = "org.postgresql.Driver";
  private static DBManager instance;

  public static DBManager get(String driver, String url, String username, String password)
  {
    switch(driver)
    {
      case DRIVER_POSTGRES:
        if(instance == null) instance = new DBManagerPostgres(driver, url, username, password);
        break;
      default:
        throw new UnsupportedOperationException("Not yet implemented");
    }
    return instance;
  }
}
