package lol.haha;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class MyListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        System.out.println("PLAYER_JOINED");
        Bukkit.getServer().broadcastMessage("player joined");
    }

    @EventHandler
    public void onPlayerStatisticIncrement(PlayerStatisticIncrementEvent event) {
        if (event.getStatistic() == Statistic.JUMP) {
            // Player has jumped
            System.out.println("PLAYER_JUMPED");
            Bukkit.getServer().broadcastMessage("jump");

            Player player = event.getPlayer();
            player.setVelocity(player.getLocation().getDirection().multiply(3.0D));

            Bukkit.getScheduler().runTaskLater(Haha.getInstance(), new Runnable() {
                @Override
                public void run() {
                    player.setVelocity(player.getLocation().getDirection().multiply(3.0D));
                }
            }, 4);

            deleteSurroundingBlocks(player.getLocation());

            Bukkit.getScheduler().runTaskLater(Haha.getInstance(), new Runnable() {
                @Override
                public void run() {
                    System.out.println("SPAWN_BLOCK");
                    spawnBlockUnderPlayer(player);
                }
            }, 30);
            //player.damage(1);
        }
    }

    public void spawnBlockUnderPlayer(Player player) {
        Block downBlock = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
        downBlock.setType(Material.DIRT);
        downBlock.getRelative(BlockFace.NORTH).setType(Material.DIRT);
        downBlock.getRelative(BlockFace.SOUTH).setType(Material.DIRT);
        downBlock.getRelative(BlockFace.EAST).setType(Material.DIRT);
        downBlock.getRelative(BlockFace.WEST).setType(Material.DIRT);
        downBlock.getRelative(BlockFace.NORTH_EAST).setType(Material.DIRT);
        downBlock.getRelative(BlockFace.SOUTH_EAST).setType(Material.DIRT);
        downBlock.getRelative(BlockFace.NORTH_WEST).setType(Material.DIRT);
        downBlock.getRelative(BlockFace.SOUTH_WEST).setType(Material.DIRT);
    }

    public void deleteSurroundingBlocks(Location location) {

        location.getBlock().getRelative(BlockFace.DOWN).setType(Material.AIR);

        List<Block> blocks = getNearbyBlocks(location, 1);
        for (Block block : blocks) {
            block.setType(Material.AIR);
        }
        //Bukkit.getPluginManager().callEvent(new EntityDamageEvent(player, EntityDamageEvent.DamageCause.SUICIDE, 1.0));
    }

    public static List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<Block>();
        for(int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for(int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for(int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }
}
