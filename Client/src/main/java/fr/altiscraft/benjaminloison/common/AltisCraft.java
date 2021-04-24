package fr.altiscraft.benjaminloison.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.lwjgl.opengl.Display;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import de.matthiasmann.twl.utils.PNGDecoder;
import fr.altiscraft.benjaminloison.api.Config;
import fr.altiscraft.benjaminloison.api.ContentLoader;
import fr.altiscraft.benjaminloison.api.DrinkRegistry;
import fr.altiscraft.benjaminloison.api.FileAPI;
import fr.altiscraft.benjaminloison.api.ItemLoader;
import fr.altiscraft.benjaminloison.api.PlayerContainer;
import fr.altiscraft.benjaminloison.blocks.ATM;
import fr.altiscraft.benjaminloison.blocks.Bank;
import fr.altiscraft.benjaminloison.blocks.Basket;
import fr.altiscraft.benjaminloison.blocks.Bin;
import fr.altiscraft.benjaminloison.blocks.BlockFlansWorkbench;
import fr.altiscraft.benjaminloison.blocks.BlockInvisible;
import fr.altiscraft.benjaminloison.blocks.BlockMod;
import fr.altiscraft.benjaminloison.blocks.Coco;
import fr.altiscraft.benjaminloison.blocks.EmptyFireplace;
import fr.altiscraft.benjaminloison.blocks.Fan;
import fr.altiscraft.benjaminloison.blocks.Fence;
import fr.altiscraft.benjaminloison.blocks.Fireplace;
import fr.altiscraft.benjaminloison.blocks.Flag;
import fr.altiscraft.benjaminloison.blocks.FloorLamp;
import fr.altiscraft.benjaminloison.blocks.NoEntry;
import fr.altiscraft.benjaminloison.blocks.Pedestrian;
import fr.altiscraft.benjaminloison.blocks.Plant;
import fr.altiscraft.benjaminloison.blocks.STOP;
import fr.altiscraft.benjaminloison.blocks.Shutter;
import fr.altiscraft.benjaminloison.blocks.Stool;
import fr.altiscraft.benjaminloison.blocks.VATM;
import fr.altiscraft.benjaminloison.blocks.VFloorLamp;
import fr.altiscraft.benjaminloison.common.driveables.EntityMecha;
import fr.altiscraft.benjaminloison.common.driveables.EntityPlane;
import fr.altiscraft.benjaminloison.common.driveables.EntitySeat;
import fr.altiscraft.benjaminloison.common.driveables.EntityVehicle;
import fr.altiscraft.benjaminloison.common.driveables.EntityWheel;
import fr.altiscraft.benjaminloison.common.driveables.EnumType;
import fr.altiscraft.benjaminloison.common.driveables.InfoType;
import fr.altiscraft.benjaminloison.common.driveables.ItemMecha;
import fr.altiscraft.benjaminloison.common.driveables.ItemMechaAddon;
import fr.altiscraft.benjaminloison.common.driveables.ItemPart;
import fr.altiscraft.benjaminloison.common.driveables.ItemPlane;
import fr.altiscraft.benjaminloison.common.driveables.ItemVehicle;
import fr.altiscraft.benjaminloison.common.driveables.MechaItemType;
import fr.altiscraft.benjaminloison.common.driveables.MechaType;
import fr.altiscraft.benjaminloison.common.driveables.PartType;
import fr.altiscraft.benjaminloison.common.driveables.PlaneType;
import fr.altiscraft.benjaminloison.common.driveables.TypeFile;
import fr.altiscraft.benjaminloison.common.driveables.VehicleType;
import fr.altiscraft.benjaminloison.common.guns.AAGunType;
import fr.altiscraft.benjaminloison.common.guns.AttachmentType;
import fr.altiscraft.benjaminloison.common.guns.BulletType;
import fr.altiscraft.benjaminloison.common.guns.EntityAAGun;
import fr.altiscraft.benjaminloison.common.guns.EntityBullet;
import fr.altiscraft.benjaminloison.common.guns.EntityGrenade;
import fr.altiscraft.benjaminloison.common.guns.EntityMG;
import fr.altiscraft.benjaminloison.common.guns.EntityParachute;
import fr.altiscraft.benjaminloison.common.guns.GrenadeType;
import fr.altiscraft.benjaminloison.common.guns.GunType;
import fr.altiscraft.benjaminloison.common.guns.ItemAAGun;
import fr.altiscraft.benjaminloison.common.guns.ItemAttachment;
import fr.altiscraft.benjaminloison.common.guns.ItemBullet;
import fr.altiscraft.benjaminloison.common.guns.ItemGrenade;
import fr.altiscraft.benjaminloison.common.guns.ItemGun;
import fr.altiscraft.benjaminloison.common.guns.ItemTool;
import fr.altiscraft.benjaminloison.common.guns.ToolType;
import fr.altiscraft.benjaminloison.common.teams.ArmourType;
import fr.altiscraft.benjaminloison.common.teams.ItemTeamArmour;
import fr.altiscraft.benjaminloison.common.teams.PlayerHandler;
import fr.altiscraft.benjaminloison.entity.EntityCustomizableNPC;
import fr.altiscraft.benjaminloison.events.CommonTickHandler;
import fr.altiscraft.benjaminloison.events.EventSystem;
import fr.altiscraft.benjaminloison.events.ForgeT4EventHandler;
import fr.altiscraft.benjaminloison.events.RenderTickHandler;
import fr.altiscraft.benjaminloison.events.T4EventHandler;
import fr.altiscraft.benjaminloison.gui.BrowserScreen;
import fr.altiscraft.benjaminloison.gui.CommonGuiHandler;
import fr.altiscraft.benjaminloison.gui.F3Menu;
import fr.altiscraft.benjaminloison.internet.api.API;
import fr.altiscraft.benjaminloison.internet.api.IBrowser;
import fr.altiscraft.benjaminloison.internet.api.IDisplayHandler;
import fr.altiscraft.benjaminloison.internet.api.IJSQueryCallback;
import fr.altiscraft.benjaminloison.internet.api.IJSQueryHandler;
import fr.altiscraft.benjaminloison.internet.api.MCEFApi;
import fr.altiscraft.benjaminloison.items.Armor;
import fr.altiscraft.benjaminloison.items.Backpack;
import fr.altiscraft.benjaminloison.items.CustomDrink;
import fr.altiscraft.benjaminloison.items.CustomItem;
import fr.altiscraft.benjaminloison.items.Drink;
import fr.altiscraft.benjaminloison.items.ExplosiveVest;
import fr.altiscraft.benjaminloison.items.Food;
import fr.altiscraft.benjaminloison.items.GPS;
import fr.altiscraft.benjaminloison.items.GangGPS;
import fr.altiscraft.benjaminloison.items.Hammer;
import fr.altiscraft.benjaminloison.items.Map;
import fr.altiscraft.benjaminloison.items.Phone;
import fr.altiscraft.benjaminloison.items.Pickaxe;
import fr.altiscraft.benjaminloison.items.Spade;
import fr.altiscraft.benjaminloison.model.ModelATM;
import fr.altiscraft.benjaminloison.model.ModelBasket;
import fr.altiscraft.benjaminloison.model.ModelBin;
import fr.altiscraft.benjaminloison.model.ModelCoco;
import fr.altiscraft.benjaminloison.model.ModelEmptyFireplace;
import fr.altiscraft.benjaminloison.model.ModelFan;
import fr.altiscraft.benjaminloison.model.ModelFence;
import fr.altiscraft.benjaminloison.model.ModelFireplace;
import fr.altiscraft.benjaminloison.model.ModelFlag;
import fr.altiscraft.benjaminloison.model.ModelFloorLamp;
import fr.altiscraft.benjaminloison.model.ModelNoEntry;
import fr.altiscraft.benjaminloison.model.ModelSTOP;
import fr.altiscraft.benjaminloison.model.ModelShutter;
import fr.altiscraft.benjaminloison.model.ModelStool;
import fr.altiscraft.benjaminloison.model.ModelVATM;
import fr.altiscraft.benjaminloison.model.ModelVFloorLamp;
import fr.altiscraft.benjaminloison.packets.NetworkHandler;
import fr.altiscraft.benjaminloison.packets.PacketATMRobbery;
import fr.altiscraft.benjaminloison.packets.PacketAllahAkbar;
import fr.altiscraft.benjaminloison.packets.PacketCarShop;
import fr.altiscraft.benjaminloison.packets.PacketCarkill;
import fr.altiscraft.benjaminloison.packets.PacketCreateATM;
import fr.altiscraft.benjaminloison.packets.PacketCreateGang;
import fr.altiscraft.benjaminloison.packets.PacketDeleteItemStack;
import fr.altiscraft.benjaminloison.packets.PacketDrop;
import fr.altiscraft.benjaminloison.packets.PacketGPS;
import fr.altiscraft.benjaminloison.packets.PacketGang;
import fr.altiscraft.benjaminloison.packets.PacketGarage;
import fr.altiscraft.benjaminloison.packets.PacketGiveItemStack;
import fr.altiscraft.benjaminloison.packets.PacketHandler;
import fr.altiscraft.benjaminloison.packets.PacketIdentification;
import fr.altiscraft.benjaminloison.packets.PacketInterpol;
import fr.altiscraft.benjaminloison.packets.PacketKey;
import fr.altiscraft.benjaminloison.packets.PacketLicense;
import fr.altiscraft.benjaminloison.packets.PacketLockpick;
import fr.altiscraft.benjaminloison.packets.PacketMessage;
import fr.altiscraft.benjaminloison.packets.PacketMoneyDownGang;
import fr.altiscraft.benjaminloison.packets.PacketMouseOver;
import fr.altiscraft.benjaminloison.packets.PacketNPC;
import fr.altiscraft.benjaminloison.packets.PacketPlayerFine;
import fr.altiscraft.benjaminloison.packets.PacketPound;
import fr.altiscraft.benjaminloison.packets.PacketPromoteGang;
import fr.altiscraft.benjaminloison.packets.PacketRenderCarShop;
import fr.altiscraft.benjaminloison.packets.PacketRenderGPS;
import fr.altiscraft.benjaminloison.packets.PacketRenderGang;
import fr.altiscraft.benjaminloison.packets.PacketRenderInterpol;
import fr.altiscraft.benjaminloison.packets.PacketRenderMouseOver;
import fr.altiscraft.benjaminloison.packets.PacketRestriction;
import fr.altiscraft.benjaminloison.packets.PacketSendMessage;
import fr.altiscraft.benjaminloison.packets.PacketSound;
import fr.altiscraft.benjaminloison.packets.PacketSync;
import fr.altiscraft.benjaminloison.packets.PacketTeleport;
import fr.altiscraft.benjaminloison.packets.PacketTransfer;
import fr.altiscraft.benjaminloison.packets.PacketUpdateClient1;
import fr.altiscraft.benjaminloison.packets.PacketVehicle;
import fr.altiscraft.benjaminloison.packets.PacketVehicleSell;
import fr.altiscraft.benjaminloison.proxy.ClientProxy;
import fr.altiscraft.benjaminloison.tileentity.TileEntityATM;
import fr.altiscraft.benjaminloison.tileentity.TileEntityBasket;
import fr.altiscraft.benjaminloison.tileentity.TileEntityBin;
import fr.altiscraft.benjaminloison.tileentity.TileEntityCoco;
import fr.altiscraft.benjaminloison.tileentity.TileEntityEmptyFireplace;
import fr.altiscraft.benjaminloison.tileentity.TileEntityFan;
import fr.altiscraft.benjaminloison.tileentity.TileEntityFence;
import fr.altiscraft.benjaminloison.tileentity.TileEntityFireplace;
import fr.altiscraft.benjaminloison.tileentity.TileEntityFlag;
import fr.altiscraft.benjaminloison.tileentity.TileEntityFloorLamp;
import fr.altiscraft.benjaminloison.tileentity.TileEntityNoEntry;
import fr.altiscraft.benjaminloison.tileentity.TileEntityPedestrian;
import fr.altiscraft.benjaminloison.tileentity.TileEntitySTOP;
import fr.altiscraft.benjaminloison.tileentity.TileEntityShutter;
import fr.altiscraft.benjaminloison.tileentity.TileEntityVATM;
import fr.altiscraft.benjaminloison.tileentity.TileEntityVFloorLamp;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.Language;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;

@Mod(modid = AltisCraft.MODID, name = AltisCraft.NAME, version = AltisCraft.VERSION)
public class AltisCraft implements IDisplayHandler, IJSQueryHandler
{
    private Minecraft mc = Minecraft.getMinecraft();
    public static int ticker;
    public static long lastTime;
    public static File flanDir;
    public static final float soundRange = 50, driveableUpdateRange = 200;
    public static final int numPlayerSnapshots = 20;
    public static float armourSpawnRate = 0.25F;
    public static final PacketHandler packetHandler = new PacketHandler();
    public static final PlayerHandler playerHandler = new PlayerHandler();
    public static final CommonTickHandler tickHandler = new CommonTickHandler();
    public static ArrayList<ItemBullet> bulletItems = new ArrayList<ItemBullet>();
    public static ArrayList<ItemGun> gunItems = new ArrayList<ItemGun>();
    public static ArrayList<ItemAttachment> attachmentItems = new ArrayList<ItemAttachment>();
    public static ArrayList<ItemPart> partItems = new ArrayList<ItemPart>();
    public static ArrayList<ItemPlane> planeItems = new ArrayList<ItemPlane>();
    public static ArrayList<ItemVehicle> vehicleItems = new ArrayList<ItemVehicle>();
    public static ArrayList<ItemMechaAddon> mechaToolItems = new ArrayList<ItemMechaAddon>();
    public static ArrayList<ItemMecha> mechaItems = new ArrayList<ItemMecha>();
    public static ArrayList<ItemAAGun> aaGunItems = new ArrayList<ItemAAGun>();
    public static ArrayList<ItemGrenade> grenadeItems = new ArrayList<ItemGrenade>();
    public static ArrayList<ItemTool> toolItems = new ArrayList<ItemTool>();
    public static ArrayList<ItemTeamArmour> armourItems = new ArrayList<ItemTeamArmour>();
    public static SimpleNetworkWrapper network;
    public static EventSystem eventHook = new EventSystem();
    public static Config config = new Config();
    private BrowserScreen backup;
    public static final String NAME = "AltisCraft.fr", MODID = "altiscraft", VERSION = "InDev", ICONS[] = {"/assets/" + MODID + "/textures/gui/16.png", "/assets/" + MODID + "/textures/gui/32.png", "/assets/" + MODID + "/textures/gui/128.png"};
    @Instance(MODID)
    public static AltisCraft instance;
    @SidedProxy(clientSide = "fr.altiscraft.benjaminloison.proxy.ClientProxy")
    public static ClientProxy proxy;
    public static Item explosiveVest, lockPick, creditCard, map, backpack, dynamite, phone, gps, gangGPS, altisCraft, hammer;
    public static Block bank, invisible, basket, pedestrian, stool, fireplace, emptyFireplace, shutter, fan, frenchFlag, atm, vATM, stop, noEntry, bin, vFloorLamp, floorLamp, fence, cocoBlock;
    public static ToolMaterial tools = EnumHelper.addToolMaterial("tools", 2, 1000, 6, 0, 14);
    public static CreativeTabs altisCraftTab = CreativeTabs.tabAllSearch;

    public static ArmorMaterial vest = EnumHelper.addArmorMaterial("vest", 1, new int[] {0, 2}, 1), sunGlassesArmor = EnumHelper.addArmorMaterial("sun_glasses_armor", 1, new int[] {3}, 1), glassesArmor = EnumHelper.addArmorMaterial("glasses_armor", 1, new int[] {2}, 1), adjointArmor = EnumHelper.addArmorMaterial("adjoint_armor", 1, new int[] {1}, 1),
            brigadierArmor = EnumHelper.addArmorMaterial("brigadier_armor", 1, new int[] {1, 1, 1, 1}, 1), sergentArmor = EnumHelper.addArmorMaterial("sergent_armor", 1, new int[] {1, 2, 2, 1}, 1), adjudantArmor = EnumHelper.addArmorMaterial("adjudant_armor", 1, new int[] {2, 2, 2, 2}, 1), majorArmor = EnumHelper.addArmorMaterial("major_armor", 1, new int[] {2, 3, 3, 2}, 1),
            bacArmor = EnumHelper.addArmorMaterial("bac_armor", 1, new int[] {2, 3, 3, 2}, 1), aspirantArmor = EnumHelper.addArmorMaterial("aspirant_armor", 1, new int[] {3, 3, 3, 3}, 1), lieutenantArmor = EnumHelper.addArmorMaterial("lieutenant_armor", 1, new int[] {3, 4, 4, 3}, 1), capitaineArmor = EnumHelper.addArmorMaterial("capitaine_armor", 1, new int[] {4, 4, 4, 4}, 1),
            commandantArmor = EnumHelper.addArmorMaterial("commandant_armor", 1, new int[] {4, 5, 5, 4}, 1), colonelArmor = EnumHelper.addArmorMaterial("armorColonel", 1, new int[] {5, 5, 5, 5}, 1), generalArmor = EnumHelper.addArmorMaterial("general_armor", 1, new int[] {5, 5, 5, 5}, 1), outfit0 = EnumHelper.addArmorMaterial("outfit0", 1, new int[] {1, 2, 2, 1}, 1),
            rebelArmor = EnumHelper.addArmorMaterial("rebel_armor", 1, new int[] {2, 3, 3, 2}, 1), gang0Armor = EnumHelper.addArmorMaterial("gang_0_armor", 1, new int[] {2, 3, 3, 2}, 1), gang1armor = EnumHelper.addArmorMaterial("gang_1_armor", 1, new int[] {2, 3, 3, 2}, 1), gang2armor = EnumHelper.addArmorMaterial("gang_2_armor", 1, new int[] {2, 3, 3, 2}, 1),
            gang3armor = EnumHelper.addArmorMaterial("gang_3_armor", 1, new int[] {2, 6, 3, 2}, 1), rescueArmor = EnumHelper.addArmorMaterial("rescue_armor", 1, new int[] {1, 2, 2, 1}, 1);

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        //if(!Minecraft.getMinecraft().mcDataDir.getAbsolutePath().contains((char)46 + "" + (char)65 + "" + (char)108 + "" + (char)116 + "" + (char)105 + "" + (char)115 + "" + (char)67 + "" + (char)114 + "" + (char)97 + "" + (char)102 + "" + (char)116 + "" + (char)46 + "" + (char)102 + "" + (char)114))
        //Throwables.propagate(new Exception("Launcher non authorized !"));
        print("Pre-Initializing !");
        instance = this;
        FMLCommonHandler.instance().bus().register(eventHook);
        MinecraftForge.EVENT_BUS.register(eventHook);
        NetworkRegistry.INSTANCE.registerGuiHandler(this, eventHook);
        loadMain();
        if(!Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode().equals("fr_FR"))
        {
            Iterator<Language> langs = Minecraft.getMinecraft().getLanguageManager().getLanguages().iterator();
            while(langs.hasNext())
            {
                Language lang = langs.next();
                if(lang.getLanguageCode().equals("fr_FR"))
                {
                    Minecraft.getMinecraft().getLanguageManager().setCurrentLanguage(lang);
                    break;
                }
            }
        }
        Display.setTitle(NAME);
        ByteBuffer[] iconArray = new ByteBuffer[ICONS.length];
        for(int i = 0; i < ICONS.length; i++)
        {
            iconArray[i] = ByteBuffer.allocateDirect(1);
            try
            {
                iconArray[i] = loadIcon(ICONS[i]);
            }
            catch(Exception e)
            {}
        }
        Display.setIcon(iconArray);
        flanDir = new File(event.getModConfigurationDirectory().getParentFile(), "/mods/" + NAME + "/");
        if(!flanDir.exists())
        {
            flanDir.mkdirs();
            flanDir.mkdir();
        }
        GameRegistry.registerBlock(new BlockFlansWorkbench(), "flans_workbench");
        proxy.registerRenderers();
        readContentPacks(event);
        proxy.load();
        proxy.forceReload();
        ResourceLocation tFlag = new ResourceLocation(MODID, "textures/models/blocks/Flag.png"), tStool = new ResourceLocation(MODID, "textures/models/blocks/Stool.png"), tFence = new ResourceLocation(MODID, "textures/models/blocks/Fence.png"), tBin = new ResourceLocation(MODID, "textures/models/blocks/Bin.png"), tATM = new ResourceLocation(MODID, "textures/models/blocks/ATM.png"),
                tFan = new ResourceLocation(MODID, "textures/models/blocks/Fan.png"), tVATM = new ResourceLocation(MODID, "textures/models/blocks/VATM.png"), tCoco = new ResourceLocation(MODID, "textures/models/blocks/Coco.png"), tFireplace = new ResourceLocation(MODID, "textures/models/blocks/Fireplace.png"),
                tEmptyFireplace = new ResourceLocation(MODID, "textures/models/blocks/Empty Fireplace.png"), tFloorLamp = new ResourceLocation(MODID, "textures/models/blocks/Floor Lamp.png"), tVFloorLamp = new ResourceLocation(MODID, "textures/models/blocks/VFloor Lamp.png"), tSTOP = new ResourceLocation(MODID, "textures/models/blocks/STOP.png"),
                tNoEntry = new ResourceLocation(MODID, "textures/models/blocks/No Entry.png"), tBasket = new ResourceLocation(MODID, "textures/models/blocks/Basket.png"), tShutter = new ResourceLocation(MODID, "textures/models/blocks/Shutter.png");
        ModelFlag mFlag = new ModelFlag();
        ModelStool mStool = new ModelStool();
        ModelFence mFence = new ModelFence();
        ModelBin mBin = new ModelBin();
        ModelATM mATM = new ModelATM();
        ModelFan mFan = new ModelFan();
        ModelVATM mVATM = new ModelVATM();
        ModelCoco mCoco = new ModelCoco();
        ModelFireplace mFireplace = new ModelFireplace();
        ModelEmptyFireplace mEmptyFireplace = new ModelEmptyFireplace();
        ModelFloorLamp mFloorLamp = new ModelFloorLamp();
        ModelVFloorLamp mVFloorLamp = new ModelVFloorLamp();
        ModelSTOP mStop = new ModelSTOP();
        ModelNoEntry mNoEntry = new ModelNoEntry();
        ModelBasket mBasket = new ModelBasket();
        ModelShutter mShutter = new ModelShutter();
        network = NetworkRegistry.INSTANCE.newSimpleChannel(NAME);
        network.registerMessage(PacketCreateGang.Handler.class, PacketCreateGang.class, 0, Side.SERVER);
        network.registerMessage(PacketGang.Handler.class, PacketGang.class, 1, Side.SERVER);
        network.registerMessage(PacketRenderGang.Handler.class, PacketRenderGang.class, 2, Side.CLIENT);
        network.registerMessage(PacketVehicle.Handler.class, PacketVehicle.class, 3, Side.SERVER);
        network.registerMessage(PacketIdentification.Handler.class, PacketIdentification.class, 4, Side.SERVER);
        network.registerMessage(PacketKey.Handler.class, PacketKey.class, 5, Side.SERVER);
        network.registerMessage(PacketLockpick.Handler.class, PacketLockpick.class, 6, Side.SERVER);
        network.registerMessage(PacketUpdateClient1.Handler.class, PacketUpdateClient1.class, 7, Side.CLIENT);
        network.registerMessage(PacketCreateATM.Handler.class, PacketCreateATM.class, 8, Side.SERVER);
        network.registerMessage(PacketLicense.Handler.class, PacketLicense.class, 9, Side.SERVER);
        network.registerMessage(PacketMoneyDownGang.Handler.class, PacketMoneyDownGang.class, 10, Side.SERVER);
        network.registerMessage(PacketMessage.Handler.class, PacketMessage.class, 11, Side.CLIENT);
        network.registerMessage(PacketATMRobbery.Handler.class, PacketATMRobbery.class, 12, Side.SERVER);
        network.registerMessage(PacketTeleport.Handler.class, PacketTeleport.class, 13, Side.SERVER);
        network.registerMessage(PacketNPC.Handler.class, PacketNPC.class, 14, Side.SERVER);
        network.registerMessage(PacketCarkill.Handler.class, PacketCarkill.class, 15, Side.SERVER);
        network.registerMessage(PacketInterpol.Handler.class, PacketInterpol.class, 16, Side.SERVER);
        network.registerMessage(PacketSendMessage.Handler.class, PacketSendMessage.class, 17, Side.SERVER);
        network.registerMessage(PacketPound.Handler.class, PacketPound.class, 18, Side.SERVER);
        network.registerMessage(PacketSync.Handler.class, PacketSync.class, 19, Side.SERVER);
        network.registerMessage(PacketTransfer.Handler.class, PacketTransfer.class, 20, Side.SERVER);
        network.registerMessage(PacketRenderInterpol.Handler.class, PacketRenderInterpol.class, 21, Side.CLIENT);
        network.registerMessage(PacketPlayerFine.Handler.class, PacketPlayerFine.class, 22, Side.SERVER);
        network.registerMessage(PacketMouseOver.Handler.class, PacketMouseOver.class, 23, Side.SERVER);
        network.registerMessage(PacketRenderMouseOver.Handler.class, PacketRenderMouseOver.class, 24, Side.CLIENT);
        network.registerMessage(PacketRestriction.Handler.class, PacketRestriction.class, 25, Side.SERVER);
        network.registerMessage(PacketAllahAkbar.Handler.class, PacketAllahAkbar.class, 26, Side.SERVER);
        network.registerMessage(PacketCarShop.Handler.class, PacketCarShop.class, 27, Side.SERVER);
        network.registerMessage(PacketRenderCarShop.Handler.class, PacketRenderCarShop.class, 28, Side.CLIENT);
        network.registerMessage(PacketPromoteGang.Handler.class, PacketPromoteGang.class, 29, Side.SERVER);
        network.registerMessage(PacketGPS.Handler.class, PacketGPS.class, 30, Side.SERVER);
        network.registerMessage(PacketRenderGPS.Handler.class, PacketRenderGPS.class, 31, Side.CLIENT);
        network.registerMessage(PacketSound.Handler.class, PacketSound.class, 32, Side.SERVER);
        network.registerMessage(PacketDrop.Handler.class, PacketDrop.class, 33, Side.SERVER);
        network.registerMessage(PacketDeleteItemStack.Handler.class, PacketDeleteItemStack.class, 34, Side.SERVER);
        network.registerMessage(PacketGiveItemStack.Handler.class, PacketGiveItemStack.class, 35, Side.SERVER);
        network.registerMessage(PacketGarage.Handler.class, PacketGarage.class, 36, Side.SERVER);
        network.registerMessage(PacketVehicleSell.Handler.class, PacketVehicleSell.class, 37, Side.SERVER);
        creditCard = new CustomItem("credit_card", "Credit Card");
        lockPick = new CustomItem("lockpick", "Lockpick");
        backpack = new Backpack();
        map = new Map();
        altisCraft = new CustomItem("logo", "altiscraft", "AltisCraft", true);
        hammer = new Hammer();
        gps = new GPS();
        gangGPS = new GangGPS();
        phone = new Phone();
        dynamite = new CustomItem("dynamite", "Dynamite");
        explosiveVest = new ExplosiveVest();
        GameRegistry.registerItem(new Pickaxe(), "pickaxe");
        GameRegistry.registerItem(new Spade(), "spade");
        GameRegistry.registerItem(new Food("illegal", "tortoise", "Tortoise", 5, 1, 12), "tortoise");
        GameRegistry.registerItem(map, "map");
        GameRegistry.registerItem(creditCard, "credit_card");
        GameRegistry.registerItem(lockPick, "lockpick");
        GameRegistry.registerItem(new CustomItem("back", "Back"), "back");
        GameRegistry.registerItem(new CustomItem("close", "Close"), "close");
        GameRegistry.registerItem(backpack, "backpack");
        GameRegistry.registerItem(altisCraft, "altiscraft");
        GameRegistry.registerItem(new CustomItem("logo", "old_altiscraft", "Old AltisCraft", 1), "old_altiscraft");
        GameRegistry.registerItem(new CustomItem("logo", "restaurant", "Restaurant"), "restaurant");
        GameRegistry.registerItem(hammer, "hammer");
        GameRegistry.registerItem(new CustomItem("copper", "Copper", 4), "copper");
        GameRegistry.registerItem(new CustomItem("diamond", "Diamond", 2), "diamond");
        GameRegistry.registerItem(new CustomItem("cut_diamond", "Cut Diamond"), "cut_diamond");
        GameRegistry.registerItem(new CustomItem("archeology", "gold", "Gold", 4), "gold");
        GameRegistry.registerItem(gps, "gps");
        GameRegistry.registerItem(gangGPS, "gang_gps");
        GameRegistry.registerItem(new CustomItem("archeology", "crate", "Crate"), "crate");
        GameRegistry.registerItem(new CustomItem("archeology", "artifact", "Artifact", 2), "artifact");
        GameRegistry.registerItem(new CustomItem("drug", "meth_ingot", "Meth Ingot"), "meth_ingot");
        GameRegistry.registerItem(new CustomItem("drug", "lsd", "LSD", 32), "lsd");
        GameRegistry.registerItem(new CustomDrink("drug", "lsd_flask", "LSD Flask", 12, 0.5F).addPotionEffect(new PotionEffect(Potion.confusion.id, 200, 1), 0.5D).addPotionEffect(new PotionEffect(Potion.jump.id, 100, 1), 0.5D).addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 250, 1), 0.5D), "lsd_flask");
        GameRegistry.registerItem(new Food("drug", "meth", "Meth", 8, 0.5F, 2).addPotionEffect(new PotionEffect(Potion.jump.id, 400, 3), 0.5D).addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 450, 2), 0.5D).addPotionEffect(new PotionEffect(Potion.regeneration.id, 150, 3), 0.5D), "meth");
        GameRegistry.registerItem(new Food("drug", "cannabis_bag", "Cannabis Bag", 12, 0.5F, 8).addPotionEffect(new PotionEffect(Potion.confusion.id, 600, 3), 0.5D).addPotionEffect(new PotionEffect(Potion.jump.id, 200, 1), 0.5D).addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 450, 1), 0.5D), "cannabis_bag");
        GameRegistry.registerItem(new Food("drug", "bedo", "Bedo", 10, 0.5F, 4).addPotionEffect(new PotionEffect(Potion.confusion.id, 500, 3), 0.5D).addPotionEffect(new PotionEffect(Potion.jump.id, 400, 1), 0.5D).addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 550, 1), 0.5D), "bedo");
        GameRegistry.registerItem(new Food("drug", "cocaine_bag", "Cocaine Bag", 18, 1, 8).addPotionEffect(new PotionEffect(Potion.confusion.id, 1200, 3), 0.5D).addPotionEffect(new PotionEffect(Potion.jump.id, 600, 3), 0.5D).addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 1800, 2), 0.5D), "cocaine_bag");
        GameRegistry.registerItem(new Food("peach", "Peach", 1, 0.2F, 16), "peach");
        GameRegistry.registerItem(new Food("coco", "Coco", 3, 0.2F, 8), "coco");
        GameRegistry.registerItem(new Food("hamburger", "Hamburger", 5, 0.2F, 10), "hamburger");
        GameRegistry.registerItem(new Food("french_fries", "French Fries", 3, 0.2F, 10), "french_fries");
        GameRegistry.registerItem(new Food("donut", "Donut", 4, 0.2F, 10), "donut");
        GameRegistry.registerItem(new Drink("water", "Water", 8, 0, 0.2F), "water");
        GameRegistry.registerItem(new Drink("coffee", "Coffee", 4, 0, 0.2F), "coffee");
        GameRegistry.registerItem(new Drink("sprit", "Sprit", 4, 0, 0.2F), "sprit");
        GameRegistry.registerItem(new Drink("fanta", "Fanta", 4, 0, 0.2F), "fanta");
        GameRegistry.registerItem(new Drink("coca_cola", "Coca-Cola", 4, 0, 0.2F), "coca_cola");
        GameRegistry.registerItem(new Drink("coca_cola_life", "Coca-Cola Life", 4, 0, 0.2F), "coca_cola_life");
        GameRegistry.registerItem(new Drink("coca_cola_zero", "Coca-Cola Zero", 4, 0, 0.2F), "coca_cola_zero");
        GameRegistry.registerItem(new Drink("coca_cola_light", "Coca-Cola Light", 4, 0, 0.2F), "coca_cola_light");
        GameRegistry.registerItem(new CustomItem("drug", "cannabis", "Cannabis", 16), "cannabis");
        GameRegistry.registerItem(new CustomItem("drug", "cocaine_0", "Cocaine0", 64), "cocaine_0");
        GameRegistry.registerItem(new CustomItem("drug", "cocaine_1", "Cocaine1", 32), "cocaine_1");
        GameRegistry.registerItem(new CustomItem("drug", "cocaine_2", "Cocaine2", 16), "cocaine_2");
        GameRegistry.registerItem(phone, "phone");
        GameRegistry.registerItem(dynamite, "dynamite");
        GameRegistry.registerItem(explosiveVest, "explosive_vest");
        GameRegistry.registerItem(new Armor(sunGlassesArmor, 0, "Sun Glasses", "sun_glasses", "Sun Glasses"), "sun_glasses");
        GameRegistry.registerItem(new Armor(glassesArmor, 0, "Glasses", "glasses", "Glasses"), "glasses");
        GameRegistry.registerItem(new Armor(adjointArmor, 0, "Adjoint", "adjoint_helmet", "Adjoint Helmet", "police"), "adjoint_helmet");
        GameRegistry.registerItem(new Armor(rebelArmor, 0, "Rebel", "rebel_helmet", "Rebel Helmet", "rebel"), "rebel_helmet");
        GameRegistry.registerItem(new Armor(rebelArmor, 1, "Rebel", "rebel_chestplate", "Rebel Chestplate", "rebel"), "rebel_chestplate");
        GameRegistry.registerItem(new Armor(rebelArmor, 2, "Rebel", "rebel_leggings", "Rebel Leggings", "rebel"), "rebel_leggings");
        GameRegistry.registerItem(new Armor(rebelArmor, 3, "Rebel", "rebel_boots", "Rebel Boots", "rebel"), "rebel_boots");
        GameRegistry.registerItem(new Armor(gang0Armor, 0, "Gang 0", "gang_0_helmet", "Gang 0 Helmet", "rebel"), "gang_0_helmet");
        GameRegistry.registerItem(new Armor(gang0Armor, 1, "Gang 0", "gang_0_chestplate", "Gang 0 Chestplate", "rebel"), "gang_0_chestplate");
        GameRegistry.registerItem(new Armor(gang0Armor, 2, "Gang 0", "gang_0_leggings", "Gang 0 Leggings", "rebel"), "gang_0_leggings");
        GameRegistry.registerItem(new Armor(gang0Armor, 3, "Gang 0", "gang_0_boots", "Gang 0 Boots", "rebel"), "gang_0_boots");
        GameRegistry.registerItem(new Armor(gang1armor, 0, "Gang 1", "gang_1_helmet", "Gang 1 Helmet", "rebel"), "gang_1_helmet");
        GameRegistry.registerItem(new Armor(gang1armor, 1, "Gang 1", "gang_1_chestplate", "Gang 1 Chestplate", "rebel"), "gang_1_chestplate");
        GameRegistry.registerItem(new Armor(gang1armor, 2, "Gang 1", "gang_1_leggings", "Gang 1 Leggings", "rebel"), "gang_1_leggings");
        GameRegistry.registerItem(new Armor(gang1armor, 3, "Gang 1", "gang_1_boots", "Gang 1 Boots", "rebel"), "gang_1_boots");
        GameRegistry.registerItem(new Armor(gang2armor, 0, "Gang 2", "gang_2_helmet", "Gang 2 Helmet", "rebel"), "gang_2_helmet");
        GameRegistry.registerItem(new Armor(gang2armor, 1, "Gang 2", "gang_2_chestplate", "Gang 2 Chestplate", "rebel"), "gang_2_chestplate");
        GameRegistry.registerItem(new Armor(gang2armor, 2, "Gang 2", "gang_2_leggings", "Gang 2 Leggings", "rebel"), "gang_2_leggings");
        GameRegistry.registerItem(new Armor(gang2armor, 3, "Gang 2", "gang_2_boots", "Gang 2 Boots", "rebel"), "gang_2_boots");
        GameRegistry.registerItem(new Armor(gang3armor, 0, "Gang 3", "gang_3_helmet", "Gang 3 Helmet", "rebel"), "gang_3_helmet");
        GameRegistry.registerItem(new Armor(gang3armor, 1, "Gang 3", "gang_3_chestplate", "Gang 3 Chestplate", "rebel"), "gang_3_chestplate");
        GameRegistry.registerItem(new Armor(gang3armor, 2, "Gang 3", "gang_3_leggings", "Gang 3 Leggings", "rebel"), "gang_3_leggings");
        GameRegistry.registerItem(new Armor(gang3armor, 3, "Gang 3", "gang_3_boots", "Gang 3 Boots", "rebel"), "gang_3_boots");
        GameRegistry.registerItem(new Armor(rescueArmor, 0, "Rescue", "rescue_helmet", "Rescue Helmet"), "secours_helmet");
        GameRegistry.registerItem(new Armor(rescueArmor, 1, "Rescue", "rescue_chestplate", "Rescue Chestplate"), "secours_chestplate");
        GameRegistry.registerItem(new Armor(rescueArmor, 2, "Rescue", "rescue_leggings", "Rescue Leggings"), "secours_leggings");
        GameRegistry.registerItem(new Armor(rescueArmor, 3, "Rescue", "rescue_boots", "Rescue Boots"), "secours_boots");
        GameRegistry.registerItem(new Armor(brigadierArmor, 0, "Brigadier", "brigadier_helmet", "Brigadier Helmet", "police"), "brigadier_helmet");
        GameRegistry.registerItem(new Armor(brigadierArmor, 1, "Brigadier", "brigadier_chestplate", "Brigadier Chestplate", "police"), "brigadier_chestplate");
        GameRegistry.registerItem(new Armor(brigadierArmor, 2, "Brigadier", "brigadier_leggings", "Brigadier Leggings", "police"), "brigadier_leggings");
        GameRegistry.registerItem(new Armor(brigadierArmor, 3, "Brigadier", "brigadier_boots", "Brigadier Boots", "police"), "brigadier_boots");
        GameRegistry.registerItem(new Armor(sergentArmor, 0, "Sergent", "sergent_helmet", "Sergent Helmet", "police"), "sergent_helmet");
        GameRegistry.registerItem(new Armor(sergentArmor, 1, "Sergent", "sergent_chestplate", "Sergent Chestplate", "police"), "sergent_chestplate");
        GameRegistry.registerItem(new Armor(sergentArmor, 2, "Sergent", "sergent_leggings", "Sergent Leggings", "police"), "sergent_leggings");
        GameRegistry.registerItem(new Armor(sergentArmor, 3, "Sergent", "sergent_boots", "Sergent Boots", "police"), "sergent_boots");
        GameRegistry.registerItem(new Armor(adjudantArmor, 0, "Adjudant", "adjudant_helmet", "Adjudant Helmet", "police"), "adjudant_helmet");
        GameRegistry.registerItem(new Armor(adjudantArmor, 1, "Adjudant", "adjudant_chestplate", "Adjudant Chestplate", "police"), "adjudant_chestplate");
        GameRegistry.registerItem(new Armor(adjudantArmor, 2, "Adjudant", "adjudant_leggings", "Adjudant Leggings", "police"), "adjudant_leggings");
        GameRegistry.registerItem(new Armor(adjudantArmor, 3, "Adjudant", "adjudant_boots", "Adjudant Boots", "police"), "adjudant_boots");
        GameRegistry.registerItem(new Armor(majorArmor, 0, "Major", "major_helmet", "Major Helmet", "police"), "major_helmet");
        GameRegistry.registerItem(new Armor(majorArmor, 1, "Major", "major_chestplate", "Major Chestplate", "police"), "major_chestplate");
        GameRegistry.registerItem(new Armor(majorArmor, 2, "Major", "major_leggings", "Major Leggings", "police"), "major_leggings");
        GameRegistry.registerItem(new Armor(majorArmor, 3, "Major", "major_boots", "Major Boots", "police"), "major_boots");
        GameRegistry.registerItem(new Armor(bacArmor, 0, "BAC", "bac_helmet", "BAC Helmet", "police"), "bac_helmet");
        GameRegistry.registerItem(new Armor(bacArmor, 1, "BAC", "bac_chestplate", "BAC Chestplate", "police"), "bac_chestplate");
        GameRegistry.registerItem(new Armor(bacArmor, 2, "BAC", "bac_leggings", "BAC Leggings", "police"), "bac_leggings");
        GameRegistry.registerItem(new Armor(bacArmor, 3, "BAC", "bac_boots", "BAC Boots", "police"), "bac_boots");
        GameRegistry.registerItem(new Armor(aspirantArmor, 0, "Aspirant", "aspirant_helmet", "Aspirant Helmet", "police"), "aspirant_helmet");
        GameRegistry.registerItem(new Armor(aspirantArmor, 1, "Aspirant", "aspirant_chestplate", "Aspirant Chestplate", "police"), "aspirant_chestplate");
        GameRegistry.registerItem(new Armor(aspirantArmor, 2, "Aspirant", "aspirant_leggings", "Aspirant Leggings", "police"), "aspirant_leggings");
        GameRegistry.registerItem(new Armor(aspirantArmor, 3, "Aspirant", "aspirant_boots", "Aspirant Boots", "police"), "aspirant_boots");
        GameRegistry.registerItem(new Armor(lieutenantArmor, 0, "Lieutenant", "lieutenant_helmet", "Lieutenant Helmet", "police"), "lieutenant_helmet");
        GameRegistry.registerItem(new Armor(lieutenantArmor, 1, "Lieutenant", "lieutenant_chestplate", "Lieutenant Chestplate", "police"), "lieutenant_chestplate");
        GameRegistry.registerItem(new Armor(lieutenantArmor, 2, "Lieutenant", "lieutenant_leggings", "Lieutenant Leggings", "police"), "lieutenant_leggings");
        GameRegistry.registerItem(new Armor(lieutenantArmor, 3, "Lieutenant", "lieutenant_boots", "Lieutenant Boots", "police"), "lieutenant_boots");
        GameRegistry.registerItem(new Armor(capitaineArmor, 0, "Capitaine", "capitaine_helmet", "Capitaine Helmet", "police"), "capitaine_helmet");
        GameRegistry.registerItem(new Armor(capitaineArmor, 1, "Capitaine", "capitaine_chestplate", "Capitaine Chestplate", "police"), "capitaine_chestplate");
        GameRegistry.registerItem(new Armor(capitaineArmor, 2, "Capitaine", "capitaine_leggings", "Capitaine Leggings", "police"), "capitaine_leggings");
        GameRegistry.registerItem(new Armor(capitaineArmor, 3, "Capitaine", "capitaine_boots", "Capitaine Boots", "police"), "capitaine_boots");
        GameRegistry.registerItem(new Armor(commandantArmor, 0, "Commandant", "commandant_helmet", "Commandant Helmet", "police"), "commandant_helmet");
        GameRegistry.registerItem(new Armor(commandantArmor, 1, "Commandant", "commandant_chestplate", "Commandant Chestplate", "police"), "commandant_chestplate");
        GameRegistry.registerItem(new Armor(commandantArmor, 2, "Commandant", "commandant_leggings", "Commandant Leggings", "police"), "commandant_leggings");
        GameRegistry.registerItem(new Armor(commandantArmor, 3, "Commandant", "commandant_boots", "Commandant Boots", "police"), "commandant_boots");
        GameRegistry.registerItem(new Armor(colonelArmor, 0, "Colonel", "colonel_helmet", "Colonel Helmet", "police"), "colonel_helmet");
        GameRegistry.registerItem(new Armor(colonelArmor, 1, "Colonel", "colonel_chestplate", "Colonel Chestplate", "police"), "colonel_chestplate");
        GameRegistry.registerItem(new Armor(colonelArmor, 2, "Colonel", "colonel_leggings", "Colonel Leggings", "police"), "colonel_leggings");
        GameRegistry.registerItem(new Armor(colonelArmor, 3, "Colonel", "colonel_boots", "Colonel Boots", "police"), "colonel_boots");
        GameRegistry.registerItem(new Armor(generalArmor, 0, "General", "general_helmet", "General Helmet", "police"), "general_helmet");
        GameRegistry.registerItem(new Armor(generalArmor, 1, "General", "general_chestplate", "General Chestplate", "police"), "general_chestplate");
        GameRegistry.registerItem(new Armor(generalArmor, 2, "General", "general_leggings", "General Leggings", "police"), "general_leggings");
        GameRegistry.registerItem(new Armor(generalArmor, 3, "General", "general_boots", "General Boots", "police"), "general_boots");
        GameRegistry.registerItem(new Armor(outfit0, 0, "Outfit 0", "outfit_0_helmet", "Outfit 0 Helmet"), "outfit_0_helmet");
        GameRegistry.registerItem(new Armor(outfit0, 1, "Outfit 0", "outfit_0_chestplate", "Outfit 0 Chestplate"), "outfit_0_chestplate");
        GameRegistry.registerItem(new Armor(outfit0, 2, "Outfit 0", "outfit_0_leggings", "Outfit 0 Leggings"), "outfit_0_leggings");
        GameRegistry.registerItem(new Armor(outfit0, 3, "Outfit 0", "outfit_0_boots", "Outfit 0 Boots"), "outfit_0_boots");
        invisible = new BlockInvisible();
        basket = new Basket();
        pedestrian = new Pedestrian();
        shutter = new Shutter();
        frenchFlag = new Flag();
        stool = new Stool();
        fan = new Fan();
        atm = new ATM();
        vATM = new VATM();
        cocoBlock = new Coco();
        fireplace = new Fireplace();
        emptyFireplace = new EmptyFireplace();
        floorLamp = new FloorLamp();
        vFloorLamp = new VFloorLamp();
        noEntry = new NoEntry();
        stop = new STOP();
        bin = new Bin();
        fence = new Fence();
        GameRegistry.registerBlock(new BlockMod(Material.glass, "empty_glass", "Empty Glass", 1), "empty_glass");
        GameRegistry.registerBlock(new BlockMod(Material.ground, "bricks", "Bricks", 1), "bricks");
        GameRegistry.registerBlock(new BlockMod(Material.ground, "atm_texture", "ATM Texture", 1), "atm_texture");
        GameRegistry.registerBlock(new BlockMod(Material.ground, "brown_iron", "Brown Iron", 1), "brown_iron");
        GameRegistry.registerBlock(new BlockMod(Material.ground, "grey_iron", "Grey Iron", 1), "grey_iron");
        GameRegistry.registerBlock(new BlockMod(Material.ground, "light_grey_iron", "Light Grey Iron", 1), "light_grey_iron");
        GameRegistry.registerBlock(new BlockMod(Material.ground, "aluminium", "Aluminium", 1), "aluminium");
        GameRegistry.registerBlock(new BlockMod(Material.ground, "flesh", "Flesh", 1), "flesh");
        GameRegistry.registerBlock(new BlockMod(Material.ground, "brown_planks", "Brown Planks", 1), "brown_plank");
        GameRegistry.registerBlock(new BlockMod(Material.ground, "grey_bricks", "Grey Bricks", 1), "grey_bricks");
        GameRegistry.registerBlock(new BlockMod(Material.ground, "cyan", "Cyan", 1), "cyan");
        GameRegistry.registerBlock(new BlockMod(Material.ground, "red", "Red", 1), "red");
        GameRegistry.registerBlock(new BlockMod(Material.ground, "white", "White", 1), "white");
        GameRegistry.registerBlock(new BlockMod(Material.ground, "green_stone", "Green Stone", 1), "green_stone");
        GameRegistry.registerBlock(new BlockMod(Material.ground, "blue_stone", "Blue Stone", 1), "blue_stone");
        GameRegistry.registerBlock(new BlockMod(Material.ground, "white_stone_0", "White Stone 0", 1), "white_stone_0");
        GameRegistry.registerBlock(new BlockMod(Material.ground, "white_stone_1", "White Stone 1", 1), "white_stone_1");
        GameRegistry.registerBlock(new BlockMod(Material.ground, "white_stone_2", "White Stone 2", 1), "white_stone_2");
        GameRegistry.registerBlock(new BlockMod(Material.ground, "flashy_green_plastic", "Flashy Green Plastic", 1), "flashy_green_plastic");
        GameRegistry.registerBlock(new BlockMod(Material.ground, "green_plastic", "Green Plastic", 1), "green_plastic");
        GameRegistry.registerBlock(new BlockMod(Material.ground, "red_plastic", "Red Plastic", 1), "red_plastic");
        GameRegistry.registerBlock(new BlockMod(Material.ground, "white_plastic", "White Plastic", 1), "white_plastic");
        GameRegistry.registerBlock(new BlockMod(Material.ground, "blue_plastic", "Blue Plastic", 1), "blue_plastic");
        GameRegistry.registerBlock(new BlockMod(Material.ground, "road_south", "Road South", 1), "road_south");
        GameRegistry.registerBlock(new BlockMod(Material.ground, "road_west", "Road West", 1), "road_west");
        GameRegistry.registerBlock(new BlockMod(Material.ground, "road_north", "Road North", 1), "road_north");
        GameRegistry.registerBlock(new BlockMod(Material.ground, "road_east", "Road East", 1), "road_east");
        GameRegistry.registerBlock(new BlockMod(Material.ground, "road_south_west", "Road South West", 1), "road_south_west");
        GameRegistry.registerBlock(new BlockMod(Material.ground, "road_west_north", "Road West North", 1), "road_west_north");
        GameRegistry.registerBlock(new BlockMod(Material.ground, "road_north_east", "Road North East", 1), "road_north_east");
        GameRegistry.registerBlock(new BlockMod(Material.ground, "road_east_south", "Road East South", 1), "road_east_south");
        GameRegistry.registerBlock(new BlockMod(Material.ground, "road", "Road", 1), "road");
        GameRegistry.registerBlock(new BlockMod(Material.leaves, "peach_leaves", "Peach Leaves", 7.5F), "peach_leaves");
        GameRegistry.registerBlock(new BlockMod(Material.leaves, "apple_leaves", "Apple Leaves", 7.5F), "apple_leaves");
        GameRegistry.registerBlock(new BlockMod(Material.rock, "copper_ore", "Copper Ore", 7.5F), "copper_ore");
        GameRegistry.registerBlock(new BlockMod(Material.rock, "meth_ore", "Meth Ore", 25.5F), "meth_ore");
        GameRegistry.registerBlock(new Plant("cannabis_plant", "Cannabis", 1), "cannabis_plant");
        GameRegistry.registerBlock(new Plant("cocaine_plant", "Cocaine", 1.5F), "cocaine_plant");
        GameRegistry.registerBlock(new Bank(), "bank");
        GameRegistry.registerBlock(invisible, "invisible");
        GameRegistry.registerBlock(basket, "basket");
        GameRegistry.registerBlock(pedestrian, "pedestrian");
        GameRegistry.registerBlock(shutter, "shutter");
        GameRegistry.registerBlock(frenchFlag, "french_flag");
        GameRegistry.registerBlock(stool, "stool");
        GameRegistry.registerBlock(fan, "fan");
        GameRegistry.registerBlock(atm, "atm");
        GameRegistry.registerBlock(vATM, "v_atm");
        GameRegistry.registerBlock(cocoBlock, "coco_block");
        GameRegistry.registerBlock(fireplace, "fireplace");
        GameRegistry.registerBlock(emptyFireplace, "empty_fireplace");
        GameRegistry.registerBlock(floorLamp, "floor_lamp");
        GameRegistry.registerBlock(vFloorLamp, "v_floor_lamp");
        GameRegistry.registerBlock(noEntry, "no_entry");
        GameRegistry.registerBlock(stop, "stop");
        GameRegistry.registerBlock(bin, "bin");
        GameRegistry.registerBlock(fence, "fence");
        GameRegistry.registerTileEntity(TileEntityBasket.class, AltisCraft.MODID + ":basket");
        GameRegistry.registerTileEntity(TileEntityPedestrian.class, AltisCraft.MODID + ":pedestrian");
        GameRegistry.registerTileEntity(TileEntityShutter.class, AltisCraft.MODID + ":shutter");
        GameRegistry.registerTileEntity(TileEntityFlag.class, AltisCraft.MODID + ":flag");
        GameRegistry.registerTileEntity(TileEntityFence.class, AltisCraft.MODID + ":stool");
        GameRegistry.registerTileEntity(TileEntityFan.class, AltisCraft.MODID + ":fan");
        GameRegistry.registerTileEntity(TileEntityATM.class, AltisCraft.MODID + ":atm");
        GameRegistry.registerTileEntity(TileEntityVATM.class, AltisCraft.MODID + ":v_atm");
        GameRegistry.registerTileEntity(TileEntityCoco.class, AltisCraft.MODID + ":coco");
        GameRegistry.registerTileEntity(TileEntityFireplace.class, AltisCraft.MODID + ":fireplace");
        GameRegistry.registerTileEntity(TileEntityEmptyFireplace.class, AltisCraft.MODID + ":empty_fireplace");
        GameRegistry.registerTileEntity(TileEntityFloorLamp.class, AltisCraft.MODID + ":floor_lamp");
        GameRegistry.registerTileEntity(TileEntityVFloorLamp.class, AltisCraft.MODID + ":v_floor_lamp");
        GameRegistry.registerTileEntity(TileEntityNoEntry.class, AltisCraft.MODID + ":no_entry");
        GameRegistry.registerTileEntity(TileEntitySTOP.class, AltisCraft.MODID + ":stop");
        GameRegistry.registerTileEntity(TileEntityBin.class, AltisCraft.MODID + ":bin");
        GameRegistry.registerTileEntity(TileEntityFence.class, AltisCraft.MODID + ":fence");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        FMLCommonHandler.instance().bus().register(this);
        API api = MCEFApi.getAPI();
        if(api == null)
            return;
        api.registerDisplayHandler(this);
        api.registerJSQueryHandler(this);
        FMLCommonHandler.instance().bus().register(new T4EventHandler());
        MinecraftForge.EVENT_BUS.register(new ForgeT4EventHandler());
        packetHandler.initialise();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new CommonGuiHandler());
        for(InfoType type : InfoType.infoTypes)
            type.addRecipe();
        EntityRegistry.registerGlobalEntityID(EntityCustomizableNPC.class, "customizable.npc", EntityRegistry.findGlobalUniqueEntityId(), 0, 0);
        EntityRegistry.registerModEntity(EntityCustomizableNPC.class, "customizable.npc", 420, instance, 40, 1, true);
        EntityRegistry.registerGlobalEntityID(EntityPlane.class, "Plane", EntityRegistry.findGlobalUniqueEntityId());
        EntityRegistry.registerModEntity(EntityPlane.class, "Plane", 90, this, 250, 3, false);
        EntityRegistry.registerGlobalEntityID(EntityVehicle.class, "Vehicle", EntityRegistry.findGlobalUniqueEntityId());
        EntityRegistry.registerModEntity(EntityVehicle.class, "Vehicle", 95, this, 250, 10, false);
        EntityRegistry.registerGlobalEntityID(EntitySeat.class, "Seat", EntityRegistry.findGlobalUniqueEntityId());
        EntityRegistry.registerModEntity(EntitySeat.class, "Seat", 99, this, 250, 20, false);
        EntityRegistry.registerGlobalEntityID(EntityWheel.class, "Wheel", EntityRegistry.findGlobalUniqueEntityId());
        EntityRegistry.registerModEntity(EntityWheel.class, "Wheel", 103, this, 250, 20, false);
        EntityRegistry.registerGlobalEntityID(EntityParachute.class, "Parachute", EntityRegistry.findGlobalUniqueEntityId());
        EntityRegistry.registerModEntity(EntityParachute.class, "Parachute", 101, this, 40, 20, false);
        EntityRegistry.registerGlobalEntityID(EntityMecha.class, "Mecha", EntityRegistry.findGlobalUniqueEntityId());
        EntityRegistry.registerModEntity(EntityMecha.class, "Mecha", 102, this, 250, 20, false);
        EntityRegistry.registerModEntity(EntityBullet.class, "Bullet", 96, this, 40, 100, false);
        EntityRegistry.registerGlobalEntityID(EntityGrenade.class, "Grenade", EntityRegistry.findGlobalUniqueEntityId());
        EntityRegistry.registerModEntity(EntityGrenade.class, "Grenade", 100, this, 40, 100, true);
        EntityRegistry.registerGlobalEntityID(EntityMG.class, "MG", EntityRegistry.findGlobalUniqueEntityId());
        EntityRegistry.registerModEntity(EntityMG.class, "MG", 91, this, 40, 5, true);
        EntityRegistry.registerGlobalEntityID(EntityAAGun.class, "AAGun", EntityRegistry.findGlobalUniqueEntityId());
        EntityRegistry.registerModEntity(EntityAAGun.class, "AAGun", 92, this, 40, 500, false);
        FMLCommonHandler.instance().bus().register(instance);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new F3Menu(Minecraft.getMinecraft()));
        FMLCommonHandler.instance().bus().register(new RenderTickHandler());
        packetHandler.postInitialise();
        proxy.registerRender();
        new DrinkRegistry();
        if(!FileAPI.namesList.exists())
            FileAPI.write(FileAPI.namesList, "");
        print(I18n.format("initialized.post"));
    }

    public void loadMain()
    {
        new NetworkHandler();
        new ItemLoader();
        new ContentLoader();
    }

    public static String getMinecraftDir()
    {
        try
        {
            Field mcDataDir = Loader.class.getDeclaredField("minecraftDir");
            if(mcDataDir != null)
            {
                mcDataDir.setAccessible(true);
                return ((File)mcDataDir.get(null)).getAbsolutePath();
            }
            throw new Exception();
        }
        catch(Exception e)
        {
            return null;
        }
    }

    private ByteBuffer loadIcon(String path)
    {
        try
        {
            InputStream inputStream = getClass().getResourceAsStream(path);
            PNGDecoder decoder = new PNGDecoder(inputStream);
            ByteBuffer bytebuf = ByteBuffer.allocateDirect(decoder.getWidth() * decoder.getHeight() * 4);
            decoder.decode(bytebuf, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
            bytebuf.flip();
            inputStream.close();
            return bytebuf;
        }
        catch(Exception e)
        {}
        return null;
    }

    @SubscribeEvent
    public void playerDrops(PlayerDropsEvent event)
    {
        for(int i = event.drops.size() - 1; i >= 0; i--)
        {
            InfoType type = InfoType.getType(event.drops.get(i).getEntityItem());
            if(type != null && !type.canDrop)
                event.drops.remove(i);
        }
    }

    @SubscribeEvent
    public void playerDrops(ItemTossEvent event)
    {
        InfoType type = InfoType.getType(event.entityItem.getEntityItem());
        if(type != null && !type.canDrop)
            event.setCanceled(true);
    }

    @SubscribeEvent
    public void onLivingSpecialSpawn(LivingSpawnEvent.CheckSpawn event)
    {
        if(event.world.rand.nextDouble() < armourSpawnRate && event.entityLiving instanceof EntityZombie || event.entityLiving instanceof EntitySkeleton)
            if(event.world.rand.nextBoolean() && ArmourType.armours.size() > 0)
            {
                ArmourType armour = ArmourType.armours.get(event.world.rand.nextInt(ArmourType.armours.size()));
                if(armour != null && armour.type != 2)
                    event.entityLiving.setCurrentItemOrArmor(armour.type + 1, new ItemStack(armour.item));
            }
    }

    private void getTypeFiles(List<File> contentPacks)
    {
        for(File contentPack : contentPacks)
            try
            {
                ZipInputStream zipStream = new ZipInputStream(new FileInputStream(contentPack));
                BufferedReader reader = new BufferedReader(new InputStreamReader(zipStream));
                ZipEntry zipEntry = zipStream.getNextEntry();
                do
                {
                    zipEntry = zipStream.getNextEntry();
                    if(zipEntry == null)
                        continue;
                    TypeFile typeFile = null;
                    for(EnumType type : EnumType.values())
                        if(zipEntry.getName().startsWith(type.folderName + "/") && zipEntry.getName().split(type.folderName + "/").length > 1 && zipEntry.getName().split(type.folderName + "/")[1].length() > 0)
                        {
                            String[] splitName = zipEntry.getName().split("/");
                            typeFile = new TypeFile(type, splitName[splitName.length - 1].split("\\.")[0]);
                        }
                    if(typeFile == null)
                        continue;
                    for(;;)
                    {
                        String line = null;
                        try
                        {
                            line = reader.readLine();
                        }
                        catch(Exception e)
                        {
                            break;
                        }
                        if(line == null)
                            break;
                        typeFile.lines.add(line);
                    }
                }
                while(zipEntry != null);
                reader.close();
                zipStream.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
    }

    private void readContentPacks(FMLPreInitializationEvent event)
    {
        Method method = null;
        try
        {
            method = (URLClassLoader.class).getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
        }
        catch(Exception e)
        {
            print("Failed to get class loader. All content loading will now fail.");
            e.printStackTrace();
        }
        List<File> contentPacks = proxy.getContentList(method, (MinecraftServer.class).getClassLoader());
        getTypeFiles(contentPacks);
        for(EnumType type : EnumType.values())
        {
            Class<? extends InfoType> typeClass = type.getTypeClass();
            for(TypeFile typeFile : TypeFile.files.get(type))
            {
                try
                {
                    InfoType infoType = (typeClass.getConstructor(TypeFile.class).newInstance(typeFile));
                    infoType.read(typeFile);
                    switch(type)
                    {
                        case bullet:
                            bulletItems.add((ItemBullet)new ItemBullet((BulletType)infoType).setUnlocalizedName(infoType.shortName));
                            break;
                        case attachment:
                            attachmentItems.add((ItemAttachment)new ItemAttachment((AttachmentType)infoType).setUnlocalizedName(infoType.shortName));
                            break;
                        case gun:
                            gunItems.add((ItemGun)new ItemGun((GunType)infoType).setUnlocalizedName(infoType.shortName));
                            break;
                        case grenade:
                            grenadeItems.add((ItemGrenade)new ItemGrenade((GrenadeType)infoType).setUnlocalizedName(infoType.shortName));
                            break;
                        case part:
                            partItems.add((ItemPart)new ItemPart((PartType)infoType).setUnlocalizedName(infoType.shortName));
                            break;
                        case plane:
                            planeItems.add((ItemPlane)new ItemPlane((PlaneType)infoType).setUnlocalizedName(infoType.shortName));
                            break;
                        case vehicle:
                            vehicleItems.add((ItemVehicle)new ItemVehicle((VehicleType)infoType).setUnlocalizedName(infoType.shortName));
                            break;
                        case aa:
                            aaGunItems.add((ItemAAGun)new ItemAAGun((AAGunType)infoType).setUnlocalizedName(infoType.shortName));
                            break;
                        case mechaItem:
                            mechaToolItems.add((ItemMechaAddon)new ItemMechaAddon((MechaItemType)infoType).setUnlocalizedName(infoType.shortName));
                            break;
                        case mecha:
                            mechaItems.add((ItemMecha)new ItemMecha((MechaType)infoType).setUnlocalizedName(infoType.shortName));
                            break;
                        case tool:
                            toolItems.add((ItemTool)new ItemTool((ToolType)infoType).setUnlocalizedName(infoType.shortName));
                            break;
                        case armour:
                            armourItems.add((ItemTeamArmour)new ItemTeamArmour((ArmourType)infoType).setUnlocalizedName(infoType.shortName));
                            break;
                        default:
                            print("Unrecognised type for " + infoType.shortName);
                            break;
                    }
                }
                catch(Exception e)
                {
                    print("Failed to add " + type.name() + " : " + typeFile.name);
                    e.printStackTrace();
                }
            }
        }
    }

    public static PacketHandler getPacketHandler()
    {
        return instance.packetHandler;
    }

    public void setBackup(BrowserScreen bu)
    {
        backup = bu;
    }

    public boolean hasBackup()
    {
        return(backup != null);
    }

    public void showScreen(String url)
    {
        BrowserScreen scr;
        if(mc.currentScreen instanceof BrowserScreen)
            scr = (BrowserScreen)mc.currentScreen;
        else
        {
            if(hasBackup())
                scr = backup;
            else
                scr = new BrowserScreen();
            mc.displayGuiScreen(scr);
            backup = null;
        }
        scr.loadURL(url);
    }

    public IBrowser getBrowser()
    {
        if(mc.currentScreen instanceof BrowserScreen)
            return ((BrowserScreen)mc.currentScreen).browser;
        else if(backup != null)
            return backup.browser;
        else
            return null;
    }

    /*
     * if(key.isPressed() && !(mc.currentScreen instanceof BrowserScreen))
     * {
     * mc.displayGuiScreen(hasBackup() ? backup : new BrowserScreen());
     * backup = null;
     * }
     */

    @Override
    public void onAddressChange(IBrowser browser, String url)
    {
        if(mc.currentScreen instanceof BrowserScreen)
            ((BrowserScreen)mc.currentScreen).onUrlChanged(browser, url);
        else if(hasBackup())
            backup.onUrlChanged(browser, url);
    }

    @Override
    public void onTitleChange(IBrowser browser, String title)
    {}

    @Override
    public boolean onTooltip(IBrowser browser, String text)
    {
        return false;
    }

    @Override
    public void onStatusMessage(IBrowser browser, String value)
    {}

    @Override
    public boolean onConsoleMessage(IBrowser browser, String message, String source, int line)
    {
        return false;
    }

    @Override
    public boolean handleQuery(IBrowser b, long queryId, String query, boolean persistent, IJSQueryCallback cb)
    {
        if(b == getBrowser() && query.equalsIgnoreCase("username"))
        {
            try
            {
                cb.success(mc.getSession().getUsername());
            }
            catch(Throwable t)
            {
                cb.failure(500, "Internal error.");
                t.printStackTrace();
            }
            return true;
        }

        return false;
    }

    @Override
    public void cancelQuery(IBrowser b, long queryId)
    {}

    public static void print(Object object)
    {
        System.out.println("[" + NAME + "] " + object);
    }
}