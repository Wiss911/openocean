/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.openocean.internal.messages;

import org.eclipse.smarthome.core.library.types.StringType;
import org.openhab.binding.openocean.OpenOceanBindingConstants;
import org.openhab.binding.openocean.internal.messages.ESP3Packet.ESPPacketType;

/**
 *
 * @author Daniel Weber - Initial contribution
 */
public class ESP3PacketFactory {

    public final static ESP3Packet CO_RD_VERSION = new CCMessage(1, 0, new int[] { 3 });
    public final static ESP3Packet CO_RD_IDBASE = new CCMessage(1, 0, new int[] { 8 });
    public final static ESP3Packet CO_RD_REPEATER = new CCMessage(1, 0, new int[] { 10 });

    public static ESP3Packet CO_WR_REPEATER(StringType level) {
        switch (level.toString()) {
            case OpenOceanBindingConstants.REPEATERMODE_OFF:
                return new CCMessage(3, 0, new int[] { 9, 0, 0 });
            case OpenOceanBindingConstants.REPEATERMODE_LEVEL_1:
                return new CCMessage(3, 0, new int[] { 9, 1, 1 });
            default:
                return new CCMessage(3, 0, new int[] { 9, 1, 2 });
        }
    }

    public static ESP3Packet CO_WR_SUBTEL(boolean enable) {
        if (enable) {
            return new CCMessage(2, 0, new int[] { 17, 1 });
        } else {
            return new CCMessage(2, 0, new int[] { 17, 0 });
        }
    }

    public static ESP3Packet BuildPacket(int dataLength, int optionalDataLength, int packetType, int[] payload) {
        ESPPacketType type = ESPPacketType.getPacketType(packetType);

        switch (type) {
            case RESPONSE:
                return new Response(dataLength, optionalDataLength, payload);
            case RADIO_ERP1:
                return new ERP1Message(dataLength, optionalDataLength, payload);
            default:
                return null;
        }
    }
}
