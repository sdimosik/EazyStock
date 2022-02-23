package com.sdimosikvip.data.db.favourite.mapper

import com.sdimosikvip.data.db.favourite.models.FavouriteTickerDB
import com.sdimosikvip.domain.mapper.BaseMapper
import com.sdimosikvip.domain.models.FavouriteTickerDomain

class FavouriteTickerDBMapper : BaseMapper<FavouriteTickerDB, FavouriteTickerDomain> {
    override fun transform(o: FavouriteTickerDB): FavouriteTickerDomain =
        FavouriteTickerDomain(o.tickerId)

    override fun reverseTransform(o: FavouriteTickerDomain): FavouriteTickerDB =
        FavouriteTickerDB(o.tickerId)
}