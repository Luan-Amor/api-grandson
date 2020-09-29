-- Dados pré carregados pelo h2
--ENDEREÇOS
insert into Endereco(cep, endereco, numero, complemento)
values(72006290, 'Vicente Pires', 13, 'Chacara 191/1B');

insert into Endereco(cep, endereco, numero)
values(72006200, 'Vicente Pires', 15);
--CARTÃO DE CRÉDITO
insert into cartao_de_credito(nome_do_cartao, numero_do_cartao, codigo_de_seguranca, data_de_vencimento)
values('Cliente da silva','123-456-789-100', 1234, '2025-09-01');
--CLIENTES
insert into Cliente(nome, email, cpf, nota,data_inicio, senha, endereco_id, cartao_id) 
values('Cliente', 'cliente@email.com', '12345678998', 3, '2020-09-01 18:00:00', '123456', 2, 1);
--PARCEIROS
insert into Parceiro(nome, email, cpf, telefone, saldo, nota, senha, quantidade_servicos,
					data_inicio, endereco_id) 
values('Parceiro', 'parceiro@email.com', '12345678998', '619888888888', 0, 3, '123456', 0, '2020-09-11 18:00:00', 1L);

--Serviços
insert into Servico(quantidade_de_horas, valor, status, cliente_id, 
						parceiro_id, avaliacao_cliente, avaliacao_parceiro, horario)
values (2, 50,'ACEITO', 1,1, 3, 3, '2020-09-11 14:00:00');
insert into Servico(quantidade_de_horas, valor, status, cliente_id, 
						parceiro_id, avaliacao_cliente, avaliacao_parceiro, horario)
values (1, 25,'PENDENTE', 1,1, 3, 3, '2020-09-11 15:00:00');
insert into Servico(quantidade_de_horas, valor, status, cliente_id, 
						parceiro_id, avaliacao_cliente, avaliacao_parceiro, horario)
values (3, 75,'FINALIZADO', 1,1, 3, 3, '2020-09-11 18:00:00');