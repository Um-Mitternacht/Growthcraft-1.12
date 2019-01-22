package growthcraft.apples.common.compat.dynamictrees;

import growthcraft.apples.common.compat.dynamictrees.init.ModTrees;
import growthcraft.apples.common.compat.dynamictrees.trees.TreeApple;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class DynamicTreesCompat {
	private DynamicTreesCompat() {}

	public static boolean isAppleLeaves(IBlockState state) {
		return ModTrees.appleTree.getCommonLeaves().getDynamicLeavesState().getBlock() == state.getBlock();
	}
	
	/////////////
	
	public static void onPreInit() {
		ModTrees.appleTree = new TreeApple("apple");
		ModTrees.appleTree.onPreInit();
	}
	
	public static void onInit() {
		
	}
	
	public static void onRegisterBlocks(IForgeRegistry<Block> registry) {
		ModTrees.appleTree.onRegisterBlocks(registry);
	}
	
	public static void onRegisterBlockItems(IForgeRegistry<Item> registry) {
		ModTrees.appleTree.onRegisterBlockItems(registry);
	}
	
	@SideOnly(Side.CLIENT)
	public static void onInitColorHandler() {
		ModTrees.appleTree.onInitColorHandler();
	}
	
	@SideOnly(Side.CLIENT)
	public static void onRegisterModels() {
		ModTrees.appleTree.onRegisterModels();
	}
	
	@SideOnly(Side.CLIENT)
	public static void onSetStateMappers() {
		ModTrees.appleTree.onSetStateMappers();
	}

}