package tech.bingulhan.bsplugin;

import tech.bingulhan.bsplugin.impl.EntityDeathEventReader;

import java.io.DataInputStream;

public interface IBridgeDataReader {


    void read(DataInputStream inputStream);

    byte id();

    IBridgeDataReader[] readers = new IBridgeDataReader[]{new EntityDeathEventReader()};
}
