package com.grandson.apigrandson.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.grandson.apigrandson.models.Cliente;
import com.grandson.apigrandson.models.Parceiro;
import com.grandson.apigrandson.models.Servico;
import com.grandson.apigrandson.models.StatusServico;

public interface ServicoRepository extends JpaRepository<Servico, Long> {

	public List<Servico> findByParceiro(Parceiro parceiro);

	public List<Servico> findByCliente(Long id);
	
	@Query("select s from Servico s where s.cliente = :cliente and s.status = :status ")
	public List<Servico> findServicosStatus(@Param("cliente") Cliente cliente, 
											@Param("status") StatusServico status);
	
	@Query("select s from Servico s where s.parceiro = :parceiro and s.status = :status ")
	public List<Servico> findServicosStatus(@Param("parceiro") Parceiro parceiro, 
											@Param("status") StatusServico status);
	
	@Query("select s from Servico s where s.parceiro = :parceiro and s.status = :status or  s.status = :status1")
	public List<Servico> findServicosStatus(@Param("parceiro") Parceiro parceiro, 
											@Param("status")StatusServico status, 
											@Param("status1")StatusServico status1);

	@Query("select AVG(s.avaliacaoParceiro) from Servico s where s.parceiro = :parceiro")
	public double getMediaAvaliacaoParceiro(@Param("parceiro") Parceiro parceiro);
	
	@Query("select AVG(s.avaliacaoCliente) from Servico s where s.cliente = :cliente")
	public double getMediaAvaliacaoCliente(@Param("cliente") Cliente cliente);


}
