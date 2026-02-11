package com.denis535.game_engine_pro

public abstract class Asset()

public class BinaryAsset(
    public val Data: ByteArray,
) : Asset()

public class TextAsset(
    public val Text: String,
) : Asset()

//public class ImageAsset(
//    public val Width: UInt,
//    public val Height: UInt,
//) : Asset()
//
//public class TextureAsset(
//    public val Width: UInt,
//    public val Height: UInt,
//) : Asset()
//
//public class SoundAsset : Asset()
//
//public class MusicAsset : Asset()
//
//public class FontAsset : Asset()
//
//public class ModelAsset : Asset()
