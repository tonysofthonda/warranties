package api.local.managebpcs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import api.local.managebpcs.model.Bpcs;

@Repository
public interface BpcsRepository extends JpaRepository<Bpcs, Long> {

	@Query(value = "SELECT "
			+ "    M.TACUSN AS dealer, "
			+ "    R.CNME AS nameDelaer, "
			+ "    Z.CCDESC AS model, "
			+ "    S.SIINVD AS dateInvoice, "
			+ "    M.TAENG AS motor, "
			+ "    M.TAVIN AS vin "
			+ " FROM "
			+ " C82PARF.MTA9 AS M JOIN C82BPCSF.RCM AS R ON M.TACUSN = R.CCUST JOIN C82BPCSF.SIH AS S ON M.TADOCN = S.SIINVN AND M.TAPRFX = S.IHDPFX JOIN C82BPCSF.IIM AS I ON M.TAITEM = I.IPROD "
			+ " JOIN C82BPCSF.ZCC AS Z ON I.IREF03 = Z.CCCODE "
			+ " WHERE "
			+ " M.TAVIN = :vin AND Z.CCTABL = 'SIRF3'", nativeQuery = true)
	List<Object[]> findByVin(final String vin);
	
	@Query(value = "SELECT "
			+ "    (SELECT CCDESC FROM C82BPCSF.ZCC WHERE C82BPCSF.ZCC.CCTABL='SIRF4' AND C82BPCSF.ZCC.CCCODE = I.IREF04) AS Anio, "
			+ "    SUBSTR(CHAR (M.TAVIN), 4, 5) AS VDS, "
			+ "    (SELECT CCDESC FROM C82BPCSF.ZCC WHERE C82BPCSF.ZCC.CCTABL='SIRF3' AND C82BPCSF.ZCC.CCCODE = I.IREF03) AS Descripcion, "
			+ "    (SELECT CCDESC FROM C82BPCSF.ZCC WHERE C82BPCSF.ZCC.CCTABL='SIRF5' AND C82BPCSF.ZCC.CCCODE = I.IREF05) AS Color, "
			+ "    (SELECT CCDESC FROM C82BPCSF.ZCC WHERE C82BPCSF.ZCC.CCTABL='SIRF2' AND C82BPCSF.ZCC.CCCODE = I.IREF02) AS CCHP, "
			+ "    (SELECT CCDESC FROM C82BPCSF.ZCC WHERE C82BPCSF.ZCC.CCTABL='COUNTRY' AND C82BPCSF.ZCC.CCCODE = I.IORIGN) AS Origen, "
			+ "    I.IPROD AS Modelobpcs "
			+ " FROM C82BPCSF.IIM AS I "
			+ " JOIN C82PARF.MTA9 AS M "
			+ " ON I.IPROD = M.TAITEM "
			+ " WHERE I.IREF01 = 'MC' "
			+ " AND I.ICOND = '0' "
			+ " AND I.IPROD = :model "
			+ " FETCH FIRST 1 ROWS ONLY", nativeQuery = true)
	List<Object[]> findByModel(final String model);
	
	@Query(value = "SELECT PFCT1 FROM P82BPCSF.ESPL01 WHERE PCURR = 'USD' AND SPID='SP' AND PRKEY = :numPart", nativeQuery = true)
	List<Object[]> findByNumPart(final String numPart);

}
