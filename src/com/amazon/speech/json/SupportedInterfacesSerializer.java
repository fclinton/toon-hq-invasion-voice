/*
    Copyright 2014-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.

    Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file
    except in compliance with the License. A copy of the License is located at

        http://aws.amazon.com/apache2.0/

    or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for
    the specific language governing permissions and limitations under the License.
 */

package com.amazon.speech.json;

import java.io.IOException;

import com.amazon.speech.speechlet.SupportedInterfaces;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

class SupportedInterfacesSerializer extends StdSerializer<SupportedInterfaces> {
    SupportedInterfacesSerializer() {
        super(SupportedInterfaces.class);
    }

    @Override
    public void serialize(SupportedInterfaces supportedInterfaces, JsonGenerator jgen,
            SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        for (SerializedInterface interfaceName : SerializedInterface.values()) {
            if (supportedInterfaces.isInterfaceSupported(interfaceName.getInterfaceClass())) {
                jgen.writeFieldName(interfaceName.name());
                jgen.writeObject(supportedInterfaces.getSupportedInterface(interfaceName
                        .getInterfaceClass()));
            }
        }

        jgen.writeEndObject();
    }
}
