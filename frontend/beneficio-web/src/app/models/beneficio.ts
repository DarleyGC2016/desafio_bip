export interface Beneficio{
    id?: number;
    nome: string;
    descricao: string;
    valor: number;
    versao?: number;
    ativo?: boolean;
}

export interface Transferir{
    fromId: number;
    toId: number;
    amount: number;
}