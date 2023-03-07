package oddlyspaced.surge

class AffinityConfiguration {
    companion object {
        const val APPLICATION_PORT = 4444
        const val GENERATE_DUMMY_DATA = false
        const val RESTORE_DATA_ON_BOOT = false
        const val SAVE_DATA_TO_STORAGE = true
        const val SAVE_DATA_INTERVAL = 1000 * 10L // ms time
    }
}