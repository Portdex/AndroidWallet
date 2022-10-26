package com.application.portdex.Models

data class ExchangeRates(
    val `data`: ExRates
)

data class ExRates(
    val currency: String,
    val rates: Rates
)


data class Rates(
    val `1INCH`: String,
    val AAVE: String,
    val ABT: String,
    val ACH: String,
    val ADA: String,
    val AED: String,
    val AERGO: String,
    val AFN: String,
    val AGLD: String,
    val AIOZ: String,
    val ALCX: String,
    val ALEPH: String,
    val ALGO: String,
    val ALICE: String,
    val ALL: String,
    val AMD: String,
    val AMP: String,
    val ANG: String,
    val ANKR: String,
    val AOA: String,
    val APE: String,
    val API3: String,
    val ARPA: String,
    val ARS: String,
    val ASM: String,
    val AST: String,
    val ATA: String,
    val ATOM: String,
    val AUCTION: String,
    val AUD: String,
    val AURORA: String,
    val AVAX: String,
    val AVT: String,
    val AWG: String,
    val AXS: String,
    val AZN: String,
    val BADGER: String,
    val BAL: String,
    val BAM: String,
    val BAND: String,
    val BAT: String,
    val BBD: String,
    val BCH: String,
    val BDT: String,
    val BGN: String,
    val BHD: String,
    val BICO: String,
    val BIF: String,
    val BIT: String,
    val BLZ: String,
    val BMD: String,
    val BND: String,
    val BNT: String,
    val BOB: String,
    val BOBA: String,
    val BOND: String,
    val BRL: String,
    val BSD: String,
    val BSV: String,
    val BTC: String,
    val BTN: String,
    val BTRST: String,
    val BUSD: String,
    val BWP: String,
    val BYN: String,
    val BYR: String,
    val BZD: String,
    val C98: String,
    val CAD: String,
    val CBETH: String,
    val CDF: String,
    val CELR: String,
    val CGLD: String,
    val CHF: String,
    val CHZ: String,
    val CLF: String,
    val CLP: String,
    val CLV: String,
    val CNH: String,
    val CNY: String,
    val COMP: String,
    val COP: String,
    val COTI: String,
    val COVAL: String,
    val CRC: String,
    val CRO: String,
    val CRPT: String,
    val CRV: String,
    val CTSI: String,
    val CTX: String,
    val CUC: String,
    val CVC: String,
    val CVE: String,
    val CVX: String,
    val CZK: String,
    val DAI: String,
    val DAR: String,
    val DASH: String,
    val DDX: String,
    val DESO: String,
    val DEXT: String,
    val DIA: String,
    val DJF: String,
    val DKK: String,
    val DNT: String,
    val DOGE: String,
    val DOP: String,
    val DOT: String,
    val DREP: String,
    val DYP: String,
    val DZD: String,
    val EGP: String,
    val ELA: String,
    val ENJ: String,
    val ENS: String,
    val EOS: String,
    val ERN: String,
    val ETB: String,
    val ETC: String,
    val ETH: String,
    val ETH2: String,
    val EUR: String,
    val FARM: String,
    val FET: String,
    val FIDA: String,
    val FIL: String,
    val FIS: String,
    val FJD: String,
    val FKP: String,
    val FLOW: String,
    val FORT: String,
    val FORTH: String,
    val FOX: String,
    val FX: String,
    val GAL: String,
    val GALA: String,
    val GBP: String,
    val GBX: String,
    val GEL: String,
    val GFI: String,
    val GGP: String,
    val GHS: String,
    val GIP: String,
    val GLM: String,
    val GMD: String,
    val GMT: String,
    val GNF: String,
    val GNO: String,
    val GNT: String,
    val GODS: String,
    val GRT: String,
    val GST: String,
    val GTC: String,
    val GTQ: String,
    val GUSD: String,
    val GYD: String,
    val GYEN: String,
    val HIGH: String,
    val HKD: String,
    val HNL: String,
    val HOPR: String,
    val HRK: String,
    val HTG: String,
    val HUF: String,
    val ICP: String,
    val IDEX: String,
    val IDR: String,
    val ILS: String,
    val IMP: String,
    val IMX: String,
    val INDEX: String,
    val INJ: String,
    val INR: String,
    val INV: String,
    val IOTX: String,
    val IQD: String,
    val ISK: String,
    val JASMY: String,
    val JEP: String,
    val JMD: String,
    val JOD: String,
    val JPY: String,
    val JUP: String,
    val KEEP: String,
    val KES: String,
    val KGS: String,
    val KHR: String,
    val KMF: String,
    val KNC: String,
    val KRL: String,
    val KRW: String,
    val KSM: String,
    val KWD: String,
    val KYD: String,
    val KZT: String,
    val LAK: String,
    val LBP: String,
    val LCX: String,
    val LINK: String,
    val LKR: String,
    val LOKA: String,
    val LOOM: String,
    val LPT: String,
    val LQTY: String,
    val LRC: String,
    val LRD: String,
    val LSL: String,
    val LTC: String,
    val LYD: String,
    val MAD: String,
    val MANA: String,
    val MASK: String,
    val MATH: String,
    val MATIC: String,
    val MCO2: String,
    val MDL: String,
    val MDT: String,
    val MEDIA: String,
    val METIS: String,
    val MGA: String,
    val MINA: String,
    val MIR: String,
    val MKD: String,
    val MKR: String,
    val MLN: String,
    val MMK: String,
    val MNT: String,
    val MONA: String,
    val MOP: String,
    val MPL: String,
    val MTL: String,
    val MUR: String,
    val MUSD: String,
    val MUSE: String,
    val MVR: String,
    val MWK: String,
    val MXC: String,
    val MXN: String,
    val MYR: String,
    val MZN: String,
    val NAD: String,
    val NCT: String,
    val NEAR: String,
    val NEST: String,
    val NGN: String,
    val NIO: String,
    val NKN: String,
    val NMR: String,
    val NOK: String,
    val NPR: String,
    val NU: String,
    val NZD: String,
    val OCEAN: String,
    val OGN: String,
    val OMG: String,
    val OMR: String,
    val OOKI: String,
    val OP: String,
    val ORCA: String,
    val ORN: String,
    val OXT: String,
    val PAB: String,
    val PAX: String,
    val PEN: String,
    val PERP: String,
    val PGK: String,
    val PHP: String,
    val PKR: String,
    val PLA: String,
    val PLN: String,
    val PLU: String,
    val POLS: String,
    val POLY: String,
    val POND: String,
    val POWR: String,
    val PRO: String,
    val PRQ: String,
    val PUNDIX: String,
    val PYG: String,
    val QAR: String,
    val QNT: String,
    val QSP: String,
    val QUICK: String,
    val RAD: String,
    val RAI: String,
    val RARE: String,
    val RARI: String,
    val RBN: String,
    val REN: String,
    val REP: String,
    val REPV2: String,
    val REQ: String,
    val RGT: String,
    val RLC: String,
    val RLY: String,
    val RNDR: String,
    val RON: String,
    val ROSE: String,
    val RSD: String,
    val RUB: String,
    val RWF: String,
    val SAND: String,
    val SAR: String,
    val SBD: String,
    val SCR: String,
    val SEK: String,
    val SGD: String,
    val SHIB: String,
    val SHP: String,
    val SHPING: String,
    val SKL: String,
    val SLL: String,
    val SNT: String,
    val SNX: String,
    val SOL: String,
    val SPELL: String,
    val SRD: String,
    val SSP: String,
    val STD: String,
    val STG: String,
    val STORJ: String,
    val STX: String,
    val SUKU: String,
    val SUPER: String,
    val SUSHI: String,
    val SVC: String,
    val SWFTC: String,
    val SYLO: String,
    val SYN: String,
    val SZL: String,
    val THB: String,
    val TIME: String,
    val TJS: String,
    val TMT: String,
    val TND: String,
    val TONE: String,
    val TOP: String,
    val TRAC: String,
    val TRB: String,
    val TRIBE: String,
    val TRU: String,
    val TRY: String,
    val TTD: String,
    val TWD: String,
    val TZS: String,
    val UAH: String,
    val UGX: String,
    val UMA: String,
    val UNFI: String,
    val UNI: String,
    val UPI: String,
    val USD: String,
    val USDC: String,
    val USDT: String,
    val UST: String,
    val UYU: String,
    val UZS: String,
    val VEF: String,
    val VES: String,
    val VGX: String,
    val VND: String,
    val VUV: String,
    val WAMPL: String,
    val WBTC: String,
    val WCFG: String,
    val WLUNA: String,
    val WST: String,
    val XAF: String,
    val XAG: String,
    val XAU: String,
    val XCD: String,
    val XCN: String,
    val XDR: String,
    val XLM: String,
    val XMON: String,
    val XOF: String,
    val XPD: String,
    val XPF: String,
    val XPT: String,
    val XRP: String,
    val XTS: String,
    val XTZ: String,
    val XYO: String,
    val YER: String,
    val YFI: String,
    val YFII: String,
    val ZAR: String,
    val ZEC: String,
    val ZEN: String,
    val ZMW: String,
    val ZRX: String,
    val ZWD: String,
    val ZWL: String
)
