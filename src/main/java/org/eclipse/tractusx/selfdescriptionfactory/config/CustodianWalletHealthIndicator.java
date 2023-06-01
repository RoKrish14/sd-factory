/********************************************************************************
 * Copyright (c) 2022 T-Systems International GmbH
 * Copyright (c) 2022 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Apache License, Version 2.0 which is available at
 * https://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 ********************************************************************************/

package org.eclipse.tractusx.selfdescriptionfactory.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.net.Socket;

@Component("CustodianWalletHealthIndicator")
@Slf4j
public class CustodianWalletHealthIndicator implements HealthIndicator {

    @Value("${app.usersDetails.custodianWallet.uri}")
    private String url;

    @Override
    public Health health() {
        try (Socket socket =
            new Socket(new java.net.URL(url).getHost(),80)) {
            log.info("Custodian wallet is up");
        } catch (Exception e) {
            log.warn("Failed to connect to: {}",url);
            return Health.down()
                    .withDetail("error", e.getMessage())
                    .build();
        }
        return Health.up()
                .build();
    }
}
