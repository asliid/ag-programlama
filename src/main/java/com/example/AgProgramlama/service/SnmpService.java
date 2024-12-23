package com.example.AgProgramlama.service;

import com.example.AgProgramlama.models.Device;
import com.example.AgProgramlama.models.OidDescription;
import com.example.AgProgramlama.models.SnmpData;
import com.example.AgProgramlama.repository.DeviceRepository;
import com.example.AgProgramlama.repository.OidDescriptionRepository;
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
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SnmpService {

    @Autowired
    private SnmpDataRepository snmpDataRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private OidDescriptionRepository oidDescriptionRepository;

    public String getSnmpData(String ipAddress, String oid) throws IOException {
        // SNMP verisini almak için mevcut kod
        String snmpValue = fetchSnmpValue(ipAddress, oid);

        // Cihazı repository üzerinden alıyoruz
        Device device = deviceRepository.findByIpAddress(ipAddress)
                .orElseThrow(() -> new IllegalArgumentException("Device not found"));

        // SnmpData nesnesini oluşturuyoruz ve cihazla ilişkilendiriyoruz
        SnmpData snmpData = new SnmpData();
        snmpData.setIpAddress(ipAddress);
        snmpData.setOid(oid);
        snmpData.setValue(snmpValue);
        snmpData.setDevice(device); // Cihazla ilişkilendiriyoruz

        // Veriyi veritabanına kaydediyoruz
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

    // OID açıklamasını almak için metod
    public String getOidDescription(String oid) {
        OidDescription oidDescription = oidDescriptionRepository.findByOid(oid);

        // Eğer OID bulunamazsa null dönebilir, ona göre bir kontrol ekleyelim
        if (oidDescription == null) {
            return "No description found for the given OID.";
        }

        return oidDescription.getDescription();
    }

    public List<SnmpData> getSnmpDataByDevice(Long deviceId) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid device ID"));
        return snmpDataRepository.findByDevice(device);
    }
    // Cihazla ilişkilendirilmiş SNMP verisi ekleme
    public SnmpData addSnmpDataToDevice(Long deviceId, SnmpData snmpData) {
        // Cihazı veritabanından buluyoruz
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new IllegalArgumentException("Device not found"));

        // SNMP verisini cihazla ilişkilendiriyoruz
        snmpData.setDevice(device);

        // Veriyi kaydediyoruz
        return snmpDataRepository.save(snmpData);
    }

    public List<SnmpData> getSnmpDataInRange(String ipAddress, String oid, LocalDateTime start, LocalDateTime end) {
        return snmpDataRepository.findByIpAddressAndOidAndTimestampBetween(ipAddress, oid, start, end);
    }

}