package de.marvinleiers.mpluginapi.mpluginapi.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

public class Base64Util
{
    public static ArrayList<ItemStack> encode(String str)
    {
        ArrayList<ItemStack> items = new ArrayList<>();

        if (!str.isEmpty())
        {
            byte[] rawData = Base64.getDecoder().decode(str);

            try
            {
                ByteArrayInputStream io = new ByteArrayInputStream(rawData);
                BukkitObjectInputStream in = new BukkitObjectInputStream(io);

                int itemsCount = in.readInt();

                for (int i = 0; i < itemsCount; i++)
                    items.add((ItemStack) in.readObject());

                in.close();
            }
            catch (IOException | ClassNotFoundException ex)
            {
                System.out.println(ex);
            }
        }

        return items;
    }


    public static String saveItems(ItemStack... items)
    {
        try
        {
            ByteArrayOutputStream io = new ByteArrayOutputStream();
            BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);

            os.writeInt(items.length);

            for (int i = 0; i < items.length; i++){
                os.writeObject(items[i]);
            }

            os.flush();

            byte[] rawData = io.toByteArray();

            String encodedData = Base64.getEncoder().encodeToString(rawData);

            os.close();

            return encodedData;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
