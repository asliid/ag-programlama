package com.example.AgProgramlama.service;

import com.example.AgProgramlama.models.SnmpData;
import com.example.AgProgramlama.repository.SnmpDataRepository;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SnmpService {

    @Autowired
    private SnmpDataRepository snmpDataRepository;

    public String getSnmpData(String ipAddress, String oid) throws IOException {
        // SNMP verisini almak için mevcut kod
        String snmpValue = fetchSnmpValue(ipAddress, oid);

        // Veriyi veri tabanına kaydet
        SnmpData snmpData = new SnmpData();
        snmpData.setIpAddress(ipAddress);
        snmpData.setOid(oid);
        snmpData.setValue(snmpValue);
        snmpDataRepository.save(snmpData);

        return snmpValue;
    }

    private String fetchSnmpValue(String ipAddress, String oid) throws IOException {
        // SNMP talepleri burada işlenir
        Address targetAddress = GenericAddress.parse("udp:" + ipAddress + "/161");
        Snmp snmp = new Snmp(new DefaultUdpTransportMapping());
        snmp.listen();

        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID(oid)));
        pdu.setType(PDU.GET);

        ResponseEvent response = snmp.send(pdu, getTarget(targetAddress));
        snmp.close();

        if (response.getResponse() != null) {
            return response.getResponse().get(0).getVariable().toString();
        }
        return "No response from SNMP agent.";
    }

    private Target getTarget(Address targetAddress) {
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString("public"));
        target.setAddress(targetAddress);
        target.setRetries(2);
        target.setTimeout(1500);
        target.setVersion(SnmpConstants.version2c);
        return target;
    }

    public List<SnmpData> getAllSnmpData(){
        return snmpDataRepository.findAll();
    }

    // IP adresine göre SNMP verisini almak için
    public List<SnmpData> getSnmpDataByIp(String ipAddress) throws IOException {
        return snmpDataRepository.findByIpAddress(ipAddress); // IP'ye göre veri çekme
    }


}