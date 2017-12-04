package com.tool.util;

import java.util.Collection;

/**
 * Created by Administrator on 2017/2/22.
 */
public class CollectionUtils {
    public final static int COLLECTION_NULL = 0;
    public final static int COLLECTION_SIZE_0 = 1;
    public final static int COLLECTION_UNEMPTY = 2;

    public static int collectionState(Collection collection) {
        if (collection == null)
            return COLLECTION_NULL;
        if (collection.size() == 0)
            return COLLECTION_SIZE_0;
        else
            return COLLECTION_UNEMPTY;
    }
}
