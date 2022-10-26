package com.application.portdex.Models

data class NFTMarketData(
    val ownedNfts : List<OwnedNFTs>
)

data class OwnedNFTs(
    val contract : Contract,
    val id : ID,
    val balance : String ,
    val title : String ,
    val description : String ,
    val tokenUri : TokenURI,
    val media : List<Media>,
    val metadata : MetaDemoData,
    val timeLastUpdated : String
)
data class MetaDemoData(
    val image :String ,
    val external_url : String ,
    val name :String ,
    val description : String ,
    val attributes : Attributes ,
    val block : Block,
    val mintPrice : String ,
    val version :String ,
    val hash : String ,

)

data class Block(
    val logsBloom : String
)

data class Attributes(
    val display_type : String ,
    val value : Int ,
    val trait_type : String
)
data class Media(
    val raw : String ,
    val gateway: String,
    val thumbnail :String,
    val format : String ,
    val bytes : Long
)

data class TokenURI(
    val raw : String,
    val gateway : String
)
data class ID(
    val tokenId : String ,
    val tokenMetadata : TokenMetaData
)

data class TokenMetaData(
    val tokenType : String
)
data class Contract(
    val address : String
)