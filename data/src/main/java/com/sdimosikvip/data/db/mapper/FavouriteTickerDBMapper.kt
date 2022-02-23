package com.sdimosikvip.data.db.mapper

import com.sdimosikvip.data.db.models.FavouriteTickerDB
import com.sdimosikvip.domain.mapper.BaseMapper
import com.sdimosikvip.domain.models.FavouriteTickerDomain


class FavouriteTickerDBMapper : BaseMapper<FavouriteTickerDB, FavouriteTickerDomain> {
    override fun transform(o: FavouriteTickerDB): FavouriteTickerDomain =
        FavouriteTickerDomain(o.ticker)

    override fun reverseTransform(o: FavouriteTickerDomain): FavouriteTickerDB =
        FavouriteTickerDB(o.ticker)
}