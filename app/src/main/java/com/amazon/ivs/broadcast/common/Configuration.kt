package com.amazon.ivs.broadcast.common

import com.amazon.ivs.broadcast.models.ResolutionModel

const val AMAZON_IVS_URL = "https://aws.amazon.com/ivs/"
const val PRIVACY_POLICY_URL = "https://aws.amazon.com/privacy/"

const val SLOT_SCREEN_SHARE = "screen_share_slot"
const val SLOT_DEFAULT = "camera_slot"

val slotNames = listOf(
    SLOT_SCREEN_SHARE,
    SLOT_DEFAULT
)
object Configuration {
    const val TAG = "CustomUI"
    const val LINK = "https://fcc3ddae59ed.us-west-2.playback.live-video.net/api/video/v1/us-west-2.893648527354.channel.DmumNckWFTqz.m3u8"
    const val PORTRAIT_OPTION = "Live stream Portrait"
    const val LANDSCAPE_OPTION = "Recorded video Landscape"
    const val LIVE_PORTRAIT_LINK = "https://fcc3ddae59ed.us-west-2.playback.live-video.net/api/video/v1/us-west-2.893648527354.channel.YtnrVcQbttF0.m3u8"
    const val RECORDED_LANDSCAPE_LINK = "https://d6hwdeiig07o4.cloudfront.net/ivs/956482054022/cTo5UpKS07do/2020-07-13T22-54-42.188Z/OgRXMLtq8M11/media/hls/master.m3u8"
    const val AUTO = "Auto"
    val PlaybackRate = listOf("2.0", "1.5", "1.0", "0.5")

    const val PLAYBACK_RATE_DEFAULT = "1.0"

    const val HIDE_CONTROLS_DELAY = 5000L
}
const val INITIAL_BPS = 1500000
const val INITIAL_WIDTH = 720f
const val INITIAL_HEIGHT: Float = 1280f
const val INITIAL_FRAME_RATE: Int = 30

const val ANIMATION_DURATION = 250L
const val TIME_UNTIL_WARNING = 15000L
const val POPUP_DURATION = 10000L
const val DISABLE_DURATION = 1000L

const val BYTES_TO_MEGABYTES_FACTOR = 10485760
const val MB_TO_GB_FACTOR = 1024

const val BPS_TO_KBPS_FACTOR = 0.001
const val KPBS_TO_BPS_FACTOR = 1000
const val BPS_TO_GBPH_FACTOR = 2222222.2222222

const val FRAMERATE_LOW = 15
const val FRAMERATE_MIDDLE = 30
const val FRAMERATE_HIGH = 60

val RESOLUTION_HIGH = ResolutionModel(1080f, 1920f)
val RESOLUTION_MIDDLE = ResolutionModel(720f, 1080f)
val RESOLUTION_LOW = ResolutionModel(480f, 720f)
val CPU_TEMP_PATHS = listOf(
    "/sys/devices/system/cpu/cpu0/cpufreq/cpu_temp",
    "/sys/devices/system/cpu/cpu0/cpufreq/FakeShmoo_cpu_temp",
    "/sys/class/thermal/thermal_zone0/temp",
    "/sys/class/i2c-adapter/i2c-4/4-004c/temperature",
    "/sys/devices/platform/tegra-i2c.3/i2c-4/4-004c/temperature",
    "/sys/devices/platform/omap/omap_temp_sensor.0/temperature",
    "/sys/devices/platform/tegra_tmon/temp1_input",
    "/sys/kernel/debug/tegra_thermal/temp_tj",
    "/sys/devices/platform/s5p-tmu/temperature",
    "/sys/class/thermal/thermal_zone1/temp",
    "/sys/class/hwmon/hwmon0/device/temp1_input",
    "/sys/devices/virtual/thermal/thermal_zone1/temp",
    "/sys/devices/virtual/thermal/thermal_zone0/temp",
    "/sys/class/thermal/thermal_zone3/temp",
    "/sys/class/thermal/thermal_zone4/temp",
    "/sys/class/hwmon/hwmonX/temp1_input",
    "/sys/devices/platform/s5p-tmu/curr_temp",
    "/sys/class/thermal/thermal_zone0/temp"
)
