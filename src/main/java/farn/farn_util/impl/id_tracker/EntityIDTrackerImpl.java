package farn.farn_util.impl.id_tracker;

import farn.farn_util.api.id_tracker.IDDataTracker;

public interface EntityIDTrackerImpl {

    default IDDataTracker farnutil_getIdDataTracker() {
        throw new AssertionError();
    }

    default void farnutil_initIdDataTracker() {
    }
}
