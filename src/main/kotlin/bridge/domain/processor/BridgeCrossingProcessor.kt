package bridge.domain.processor

import bridge.common.*
import bridge.domain.moving.MovingInfo

/**
 * 사용자가 다리를 건넌 후 결과를 처리하는 역할을 한다.
 */
object BridgeCrossingProcessor {
    private val bridgeCrossingInfo = mutableListOf<Pair<MovingInfo, Boolean>>()
    private val currentMapInfo = mutableListOf<String>()
    private val bridgeIndexInfo = hashMapOf(0 to BRIDGE_NUMBER_UP, 1 to BRIDGE_NUMBER_DOWN)

    fun getCurrentMap(): List<String> {
        currentMapInfo.clear()

        repeat(BRIDGE_ROW_SIZE) { bridgeIndex ->
            currentMapInfo.add(createCurrentBridgeMap(bridgeIndexInfo[bridgeIndex]!!))
        }
        return currentMapInfo
    }

    /**
     * @param bridgeNumber 다리의 번호, 위쪽 다리는 1, 아래쪽 다리는 0으로 표현
     */
    private fun createCurrentBridgeMap(bridgeNumber: Int): String {
        var currentBridgeMap = ""
        val round = bridgeCrossingInfo.size - 1

        currentBridgeMap += "[ "
        bridgeCrossingInfo.forEachIndexed { curRound, (moving, isCrossed) ->
            currentBridgeMap += getCrossingResult(
                movingInfo = moving,
                bridgeNumber = bridgeNumber,
                isCrossed = isCrossed
            )
            currentBridgeMap += if (curRound == round) " ]" else " | "
        }
        return currentBridgeMap
    }

    private fun getCrossingResult(movingInfo: MovingInfo, bridgeNumber: Int, isCrossed: Boolean): String {
        return if (movingInfo.bridgeNumber == bridgeNumber) {
            if (isCrossed) CROSSED_SUCCESS_RESULT
            else CROSSED_FAIL_RESULT
        } else " "
    }

    fun isCrossingFail(): Boolean = bridgeCrossingInfo.any { (_, isCrossed) -> !isCrossed }

    fun updateBridgeCrossingInfo(userMovingInfo: MovingInfo, isCrossed: Boolean) =
        bridgeCrossingInfo.add(userMovingInfo to isCrossed)

    fun clearBridgeCrossingInfo() = bridgeCrossingInfo.clear()
}
