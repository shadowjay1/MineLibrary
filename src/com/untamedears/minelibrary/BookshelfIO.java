package com.untamedears.minelibrary;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.Plugin;

public class BookshelfIO {
	public final Plugin plugin;
	
	public BookshelfIO() {
		this.plugin = Bukkit.getServer().getPluginManager().getPlugin("MineLibrary");
	}
	
	public void saveBookshelf(Inventory i, Location l) {
		int x = (int)l.getX();
		int y = (int)l.getY();
		int z = (int)l.getZ();
		
		String loc = "" + x + "" + y + "" + z;
		
		this.plugin.getLogger().info("saving location: " + "books."+loc+".[number].isBook");
		
		int item = 0;
		for(ItemStack is : i) {
			this.plugin.getLogger().info("[" + item + "]");
			if(is != null && is.hasItemMeta() && is.getItemMeta() instanceof BookMeta) {
				this.plugin.getLogger().info("    isBook: true");
				BookMeta bm =  ((BookMeta)is.getItemMeta());
				
				String author = (bm.hasAuthor()) ? bm.getAuthor() : "";
				String title = (bm.hasTitle()) ? bm.getTitle() : "";
				List<String> pages = (bm.hasPages()) ? bm.getPages() : new ArrayList<String>();
				
				this.plugin.getConfig().set("books." + loc + "." + item + ".isBook", true);
				this.plugin.getConfig().set("books." + loc + "." + item + ".author", author);
				this.plugin.getConfig().set("books." + loc + "." + item + ".title", title);
				this.plugin.getConfig().set("books." + loc + "." + item + ".pages", pages);
				
				this.plugin.getLogger().info("    author:" + this.plugin.getConfig().getString("books."+loc+"."+item+".author"));
				this.plugin.getLogger().info("    title:" + this.plugin.getConfig().getString("books."+loc+"."+item+".title"));
			} else {
				this.plugin.getLogger().info("    isBook: false");
				this.plugin.getConfig().set("books."+loc+"."+item+".isBook", false);
			}
			item++;
		}
		
		this.plugin.saveConfig();
		
		this.plugin.getLogger().info("---------------end saving----------------");
	}
	
	public ItemStack[] readBookshelf(Location l) {
		int x = (int)l.getX();
		int y = (int)l.getY();
		int z = (int)l.getZ();
		
		String loc = "" + x + "" + y + "" + z;
		this.plugin.getLogger().info("reading location: " + "books."+loc);
		
//		if(this.plugin.getConfig().contains("books."+loc)) {
		ItemStack[] inventory = new ItemStack[9];
		
		for(int i = 0; i < 9; i++) {
			this.plugin.getLogger().info("[" + i + "]");
			if(this.plugin.getConfig().getBoolean("books."+loc+"."+i+".isBook")) {
				this.plugin.getLogger().info("    isBook: true");
				
				String author = this.plugin.getConfig().getString("books."+loc+"."+i+".author");
				String title = this.plugin.getConfig().getString("books."+loc+"."+i+".title");
				List<String> pages = this.plugin.getConfig().getStringList("books."+loc+"."+i+".pages");
				
				this.plugin.getLogger().info("    author:" + author);
				this.plugin.getLogger().info("    title:" + title);
				
				ItemStack is = new ItemStack(Material.WRITTEN_BOOK, 1);
				
				BookMeta bookMeta = ((BookMeta)is.getItemMeta());
				
				bookMeta.setAuthor(author);
				bookMeta.setTitle(title);
				bookMeta.setPages(pages);
				
				is.setItemMeta(bookMeta);
				
				this.plugin.getLogger().info("Author set? " + is.getItemMeta().serialize());
				
				inventory[i] = is;
			} else {
				this.plugin.getLogger().info("    isBook: false");
				ItemStack is = new ItemStack(Material.AIR, 0);
				
				inventory[i] = is;
			}
			
		}
		
		this.plugin.getLogger().info("---------------end reading----------------");
		
		return inventory;
//		}
		
//		return null;
	}
}
