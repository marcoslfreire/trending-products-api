// Configuração central da API. Mesmo rodando localmente com http-server,
// aponta direto pro backend Quarkus.
const API_BASE_URL = window.API_BASE_URL || "http://localhost:8080";

const Auth = {
    TOKEN_KEY: "trending_products_jwt",

    saveToken(token) {
        localStorage.setItem(this.TOKEN_KEY, token);
    },
    getToken() {
        return localStorage.getItem(this.TOKEN_KEY);
    },
    clear() {
        localStorage.removeItem(this.TOKEN_KEY);
    },
    isLogged() {
        return !!this.getToken();
    }
};

const Api = {
    async login(username, password) {
        const res = await fetch(`${API_BASE_URL}/auth/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username, password })
        });
        if (!res.ok) throw new Error("Usuário ou senha inválidos");
        return res.json();
    },

    async register(username, password) {
        const res = await fetch(`${API_BASE_URL}/auth/register`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username, password })
        });
        if (!res.ok) throw new Error("Não foi possível registrar (usuário já existe?)");
        return true;
    },

    async topProducts() {
        const res = await fetch(`${API_BASE_URL}/products/top`);
        if (!res.ok) throw new Error("Erro ao buscar produtos mais procurados");
        return res.json();
    },

    async searchProducts(term) {
        const res = await fetch(`${API_BASE_URL}/products/search?q=${encodeURIComponent(term)}`);
        if (!res.ok) throw new Error("Erro na pesquisa");
        return res.json();
    },

    async publishProduct(product) {
        const res = await fetch(`${API_BASE_URL}/products`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${Auth.getToken()}`
            },
            body: JSON.stringify(product)
        });
        if (!res.ok) {
            if (res.status === 401) throw new Error("Sessão expirada, faça login novamente");
            throw new Error("Erro ao publicar produto");
        }
        return res.json();
    },
    async listAll() {
        const res = await fetch(`${API_BASE_URL}/products/all`);
        if (!res.ok) throw new Error("Erro ao listar todos os produtos");
        return res.json();
    },
};

function productCardHtml(p) {
    const img = p.imageUrl || "https://picsum.photos/seed/default/120";
    const price = Number(p.price).toLocaleString("pt-BR", { style: "currency", currency: "BRL" });
    return `
        <div class="product-card" data-id="${p.id}" data-name="${p.name}"
             data-description="${p.description ?? ""}" data-price="${p.price}">
            <img src="${img}" alt="${p.name}" />
            <div class="info">
                <h3>${p.name}</h3>
                <p>${p.description ?? ""}</p>
                <span class="price">${price}</span>
                <span class="badge"> · pesquisado ${p.searchCount}x</span>
            </div>
        </div>
    `;
}