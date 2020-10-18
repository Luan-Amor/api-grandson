package com.grandson.apigrandson.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.grandson.apigrandson.models.Cliente;
import com.grandson.apigrandson.models.Endereco;
import com.grandson.apigrandson.models.Servico;

import me.pagar.model.Address;
import me.pagar.model.Billing;
import me.pagar.model.Customer;
import me.pagar.model.Document;
import me.pagar.model.Item;
import me.pagar.model.PagarMe;
import me.pagar.model.PagarMeException;
import me.pagar.model.Shipping;
import me.pagar.model.Transaction;

@Service
public class TransacaoService {

	@Value("${chave.pagarme}")
	private String apiKey;
	
	public Transaction executarTransacao(Servico servico) throws PagarMeException{
		PagarMe.init(apiKey);
		double quantidadeDeHoras = servico.getQuantidadeDeHoras();
		int amount = (int) (1250 * quantidadeDeHoras);
				
		Transaction transaction = new Transaction();
		Customer customer = preencheCliente(servico.getCliente());

		Collection<Document> documents = new ArrayList<Document>();
		Document document = new Document();
		document.setType(Document.Type.CPF);
		document.setNumber(servico.getCliente().getCpf());
		documents.add(document);
		customer.setDocuments(documents);

		Collection<String> phones = new ArrayList<String>();
		phones.add("+55"+servico.getCliente().getTelefone());
		customer.setPhoneNumbers(phones);

		Billing billing = new Billing(); 
		billing.setName(servico.getCliente().getNome());
		Address address = preencheEndereco(servico.getCliente().getEndereco());
		
		billing.setAddress(address);

		Shipping shipping = new Shipping();
		shipping.setAddress(address);
		shipping.setName("Grandson");
		shipping.setFee(1000);

		Collection<Item> items = new ArrayList<Item>();
		Item item = new Item(); 
		item.setId("s123");
		item.setQuantity(1);
		item.setTangible(Boolean.TRUE);
		item.setTitle("Serviço ");
		item.setUnitPrice(1250);

		transaction.setShipping(shipping);
		transaction.setBilling(billing);
		transaction.setItems(items);
		transaction.setPaymentMethod(Transaction.PaymentMethod.CREDIT_CARD);
		transaction.setAmount(amount);
		transaction.setCardHolderName(servico.getCliente().getCartao().getNomeDoCartao());
		transaction.setCardNumber(servico.getCliente().getCartao().getNumeroDoCartao());
		transaction.setCardCvv(String.valueOf(servico.getCliente().getCartao().getCodigoDeSeguranca()));
		transaction.setCardExpirationDate(formataData(servico.getCliente().getCartao().getDataDeVencimento())); 
		transaction.setCustomer(customer);
		transaction.save();
		
		return transaction;
		
	}


	public Transaction executarExtorno(Servico servico) throws PagarMeException {
		PagarMe.init(apiKey);		
		double quantidadeDeHoras = servico.getQuantidadeDeHoras();
		int amount = (int) (1250 * quantidadeDeHoras);
		Transaction tx = new Transaction().find(servico.getIdtransacao());
		tx.refund(amount);
		return tx;
	}
	
	private Address preencheEndereco(Endereco endereco) {
		Address address  = new Address(); 
		address.setCity(endereco.getCidade());
		address.setCountry("br");
		address.setState(endereco.getEstado());
		address.setNeighborhood(endereco.getComplemento());
		address.setStreet(endereco.getEndereco());
		address.setZipcode(String.valueOf(endereco.getCep()));
		address.setStreetNumber(String.valueOf(endereco.getNumero()));
		return address;
	}

	private Customer preencheCliente(Cliente cliente) {
		Customer customer = new Customer();
		customer.setType(Customer.Type.INDIVIDUAL);
		customer.setExternalId(String.valueOf(cliente.getId()));
		customer.setName(cliente.getNome());
		customer.setEmail(cliente.getEmail());
		customer.setCountry("br");
		return customer;
	}
	
	private String formataData(LocalDate data) {
		LocalDate ld = data;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMyy");
		
		return ld.format(formatter);
	}
	
}
