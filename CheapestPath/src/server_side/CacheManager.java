package server_side;

public interface CacheManager {
	public boolean cacheExist(String problem);

	public String getCache(String problem);

	public void addCache(String problem, String solution);
}
