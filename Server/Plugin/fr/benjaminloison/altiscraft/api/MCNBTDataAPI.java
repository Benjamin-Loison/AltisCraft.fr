package fr.benjaminloison.altiscraft.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.zip.GZIPInputStream;

import org.bukkit.Bukkit;

import com.sk89q.jnbt.ByteTag;
import com.sk89q.jnbt.CompoundTag;
import com.sk89q.jnbt.DoubleTag;
import com.sk89q.jnbt.FloatTag;
import com.sk89q.jnbt.IntTag;
import com.sk89q.jnbt.ListTag;
import com.sk89q.jnbt.LongTag;
import com.sk89q.jnbt.NBTInputStream;
import com.sk89q.jnbt.ShortTag;
import com.sk89q.jnbt.StringTag;
import com.sk89q.jnbt.Tag;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.SchematicFormat;

public class MCNBTDataAPI extends SchematicFormat
{
    private Map<String, Tag> schematic;
    private Collection<Tag> tags;

    public MCNBTDataAPI(File file)
    {
        super("MCEdit", new String[] {"mcedit", "mce"});
        try
        {
            String[] parts = file.getAbsolutePath().split(File.separatorChar + "");
            Bukkit.getPlayer(UUID.fromString(parts[parts.length - 1].replace(".dat", ""))).saveData();
            FileInputStream stream = new FileInputStream(file);
            NBTInputStream nbtStream = new NBTInputStream(new GZIPInputStream(stream));
            CompoundTag schematicTag = (CompoundTag)nbtStream.readTag();
            nbtStream.close();
            schematic = schematicTag.getValue();
            tags = schematic.values();
            stream.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public Map<String, Tag> getContents()
    {
        return schematic;
    }

    public Collection<Tag> getTags()
    {
        return tags;
    }

    public String valueKeyInCompoundTag(CompoundTag t, String key)
    {
        for(Entry<String, Tag> entry : t.getValue().entrySet())
            if(entry.getKey().equals(key))
                return entry.getValue().getValue().toString();
        return null;
    }

    public ArrayList<CompoundTag> ArrayListContentsOfListTag(List<Tag> listTags)
    {
        ArrayList<CompoundTag> ArrayCompounds = new ArrayList<CompoundTag>();
        for(Tag tag : listTags)
            if(tag instanceof CompoundTag)
                ArrayCompounds.add((CompoundTag)tag);
        return ArrayCompounds;
    }

    public ArrayList<CompoundTag> ArrayListOfKeyEquals(ArrayList<CompoundTag> t, String key, String equals)
    {
        ArrayList<CompoundTag> ArrayCompounds = new ArrayList<CompoundTag>();
        for(CompoundTag entry : t)
        {
            Tag tag = entry.getValue().get(key);
            if(tag == null)
                continue;
            if(((String)tag.getValue()).equalsIgnoreCase(equals))
                ArrayCompounds.add(entry);
        }
        return ArrayCompounds;
    }

    public boolean hasCompoundTag(String key)
    {
        if(schematic != null)
            if(schematic.get(key) instanceof CompoundTag)
                return true;
        return false;
    }

    public CompoundTag getCompoundTag(String key)
    {
        if(schematic.get(key) instanceof CompoundTag)
            return (CompoundTag)schematic.get(key);
        return null;
    }

    public List<Tag> getListTags(String key)
    {
        try
        {
            return ((ListTag)getChildTag(schematic, key, ListTag.class)).getValue();
        }
        catch(DataException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public short getShort(String key)
    {
        try
        {
            return ((ShortTag)getChildTag(schematic, key, ShortTag.class)).getValue().shortValue();
        }
        catch(DataException e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    public float getFloat(String key)
    {
        try
        {
            return ((FloatTag)getChildTag(schematic, key, FloatTag.class)).getValue();
        }
        catch(DataException e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    public double getDouble(String key)
    {
        try
        {
            return ((DoubleTag)getChildTag(schematic, key, DoubleTag.class)).getValue();
        }
        catch(DataException e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    public int getInteger(String key)
    {
        try
        {
            return ((IntTag)getChildTag(schematic, key, IntTag.class)).getValue().intValue();
        }
        catch(DataException e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    public long getLong(String key)
    {
        try
        {
            return ((LongTag)getChildTag(schematic, key, LongTag.class)).getValue().longValue();
        }
        catch(DataException e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean getBooleanFromByte(String bit)
    {
        if(Byte.parseByte(bit) == 0)
            return false;
        return true;
    }

    public Byte getByte(String key)
    {
        try
        {
            return ((ByteTag)getChildTag(schematic, key, ByteTag.class)).getValue().byteValue();
        }
        catch(DataException e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    public String getString(String key)
    {
        try
        {
            return ((StringTag)getChildTag(schematic, key, StringTag.class)).getValue();
        }
        catch(DataException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public boolean containsKey(Object key)
    {
        return schematic.containsKey(key);
    }

    public boolean containsValue(Object value)
    {
        return schematic.containsValue(value);
    }

    public boolean isEmpty()
    {
        return schematic.isEmpty();
    }

    public boolean equals(Object object)
    {
        return schematic.equals(object);
    }

    public void clear()
    {
        schematic.clear();
    }

    public int size()
    {
        return schematic.size();
    }

    private static <T extends Tag> Tag getChildTag(Map<String, Tag> items, String key, Class<T> expected) throws DataException
    {
        if(!items.containsKey(key))
            throw new DataException("Data file is missing a \"" + key + "\" tag");
        Tag tag = (Tag)items.get(key);
        if(!expected.isInstance(tag))
            throw new DataException(key + " tag is not of tag type " + expected.getName());
        return (Tag)expected.cast(tag);
    }

    @Override
    public boolean isOfFormat(File a)
    {
        return false;
    }

    @Override
    public CuboidClipboard load(File a) throws IOException, DataException
    {
        return null;
    }

    @Override
    public void save(CuboidClipboard a0, File a1) throws IOException, DataException
    {}
}