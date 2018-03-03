package growthcraft.bees.common.block;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import growthcraft.bees.Reference;
import growthcraft.bees.common.tileentity.TileEntityBeeBox;
import growthcraft.core.common.block.GrowthcraftBlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBeeBox extends GrowthcraftBlockContainer {
    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(
            0.0625 * 0, 0.0625 * 0, 0.0625 * 0,
            0.0625 * 16, 0.0625 * 16, 0.0625 * 16);
	
	private int flammability;
	private int fireSpreadSpeed;
	
	public BlockBeeBox(String unlocalizedName) {
		super(Material.WOOD);
		setTileEntityType(TileEntityBeeBox.class);
		setHardness(2.5F);
		setSoundType(SoundType.WOOD);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
	}
	
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOUNDING_BOX;
    }
    
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOX);
    }
	
    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
	
	public BlockBeeBox setFlammability(int flam)
	{
		this.flammability = flam;
		return this;
	}

	public BlockBeeBox setFireSpreadSpeed(int speed)
	{
		this.fireSpreadSpeed = speed;
		return this;
	}
	
	@Override
    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
    	return flammability;
    }

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return fireSpreadSpeed;
	}

	/*
	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void getSubBlocks(Item block, CreativeTabs tab, List list)
	{
		for (EnumMinecraftWoodType woodType : EnumMinecraftWoodType.VALUES)
		{
			list.add(new ItemStack(block, 1, woodType.meta));
		}
	}
	*/
	
	@Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);
		final TileEntityBeeBox te = getTileEntity(worldIn, pos);
		if (te != null)
			te.updateBlockTick();
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World world, BlockPos pos, Random random)
	{
		if (random.nextInt(24) == 0)
		{
			final TileEntityBeeBox te = getTileEntity(world, pos);
			if (te != null)
			{
				if (te.hasBees())
				{
//					world.playSound((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F),
//						"grcbees:buzz", 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
				}
			}
		}
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ))
			return true;
		
		if (worldIn.isRemote)
		{
			return true;
		}
		else
		{
			final TileEntityBeeBox te = getTileEntity(worldIn, pos);
			if (te != null)
			{
				playerIn.openGui(Reference.MODID, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
				return true;
			}
			return false;
		}
	}
	
	@Override
    public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
    	return EnumFacing.UP == side;
    }
	
	@Override
	public TileEntity createNewTileEntity(World world, int par2)
	{
		return new TileEntityBeeBox();
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 1;
	}
	
	@Override
	public int damageDropped(IBlockState state) {
	    return 0;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		return true;
	}
	
	/************
	 * COMPARATOR
	 ************/
	@Override
	public boolean hasComparatorInputOverride(IBlockState state)
	{
		return true;
	}

	@Override
	public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos)
	{
		final TileEntityBeeBox te = getTileEntity(world, pos);
		if (te != null)
		{
			return te.countHoney() * 15 / te.getHoneyCombMax();
		}
		return 0;
	}
}
