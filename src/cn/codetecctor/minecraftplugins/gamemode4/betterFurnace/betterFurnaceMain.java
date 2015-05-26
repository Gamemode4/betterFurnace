package cn.codetecctor.minecraftplugins.gamemode4.betterFurnace;

import net.minecraft.server.v1_8_R3.TileEntityFurnace;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class betterFurnaceMain extends JavaPlugin implements Listener{
    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        getServer().getPluginManager().registerEvents(this,this);
        //Create Recipe
        createRecipeForBetterFurnace();

    }

    public void createRecipeForBetterFurnace(){
        //ItemStack = bf
        ItemStack bf = new ItemStack(Material.FURNACE);
        ItemMeta meta = bf.getItemMeta();
        meta.setDisplayName("Better Furnace");
        String [] str =  {"Better Furnace"};
        meta.setLore(Arrays.asList(str));
        bf.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(bf);
        recipe.shape("CCC","CFC","CCC");
        recipe.setIngredient('C',Material.COBBLESTONE);
        recipe.setIngredient('F',Material.FURNACE);

        getServer().addRecipe(recipe);
    }

    @EventHandler
    public void PlayerPlaceBlockEvent(BlockPlaceEvent e){
        Player p = e.getPlayer();
        if(p.getInventory().getItemInHand()!=null && p.getInventory().getItemInHand().getItemMeta()!=null &&  p.getInventory().getItemInHand().getItemMeta().getLore()!=null && p.getInventory().getItemInHand().getItemMeta().getLore().contains("Better Furnace")){
            Block b = e.getBlockPlaced();
            b.setMetadata("BetterFurnace",new FixedMetadataValue(this,"True"));
            getLogger().info(b.getMetadata("BetterFurnace").get(0).asString());
        }

    }
    @EventHandler
    public void onSmelt(FurnaceSmeltEvent e){
        if(e.getBlock().hasMetadata("BetterFurnace") && e.getBlock().getMetadata("BetterFurnace").get(0).asString().equalsIgnoreCase("True")){
            Material[] m = {Material.IRON_INGOT, Material.GOLD_INGOT, Material.DIAMOND, Material.COAL};
            List<Material> materials = Arrays.asList(m);
            if(materials.contains(e.getResult().getType())) {
                e.getResult().setAmount(e.getResult().getAmount() * 2);
            }else if (e.getResult().getType() == Material.REDSTONE) {
                e.getResult().setAmount(e.getResult().getAmount()*4);
            }
        }
    }

}
