package me.intronate67.TotalWarfare;

import java.io.File;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class SettingsManager {
	
	private SettingsManager(){ }
	
	private static SettingsManager instance = new SettingsManager();
	
	public static SettingsManager getInstance(){
		return instance;
	}
	
	private Plugin p;
	private FileConfiguration arenas;
	private File afile;
	
	public void setup(Plugin p){
		this.p = p;
		
		if(!p.getDataFolder().exists()) p.getDataFolder().mkdir();
		
		afile = new File(p.getDataFolder(), "arenas.yml");
		if(!afile.exists()){
			try{ afile.createNewFile(); }
			catch (Exception e){ e.printStackTrace(); }
		}
		
		arenas = YamlConfiguration.loadConfiguration(afile);
	}
	@SuppressWarnings("unchecked")
	public <T> T get(String path){
		return (T) arenas.get(path);
	}
	
	public void set(String path, Object value){
		arenas.set(path, value);
		save();
	}
	
	public ConfigurationSection createConfigurationSection(String path){
		ConfigurationSection s = arenas.createSection(path);
		save();
		return s;
	}
	
	private void save(){
		try{ arenas.save(afile); }
		catch(Exception e){ e.printStackTrace(); }
	}
	
	public Plugin getPlugin(){
		return p;
	}
	
}
