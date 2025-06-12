<h1>🛠️ Guia de Contribuição</h1>

<p>Obrigado por contribuir com este projeto de <strong>Sistema de Busca Distribuído em Java</strong>! Aqui estão algumas diretrizes para manter o repositório organizado.</p>

<hr>

<h2>📌 Requisitos</h2>
<ul>
    <li>Ter o Java 11 ou superior instalado</li>
    <li>Clonar o repositório</li>
    <li>Compilar com <code>javac</code> ou usar Maven</li>
</ul>

<h2>🧱 Estrutura Recomendada</h2>
<pre><code>projetc-distributed-search/
├── cliente/
├── servidorA/
├── servidorB/
├── servidorC/
├── dados/
├── utils/
└── README.md
</code></pre>

<h2>📝 Padrão de Commits</h2>
<p>Use mensagens de commit semânticas:</p>

<table>
    <tr>
        <th>Prefixo</th>
        <th>Quando usar</th>
    </tr>
    <tr><td><code>feat:</code></td><td>Nova funcionalidade</td></tr>
    <tr><td><code>fix:</code></td><td>Correção de bugs</td></tr>
    <tr><td><code>refactor:</code></td><td>Refatoração de código</td></tr>
    <tr><td><code>docs:</code></td><td>Atualização de documentação</td></tr>
    <tr><td><code>test:</code></td><td>Adição de testes</td></tr>
    <tr><td><code>chore:</code></td><td>Manutenção (ex: .gitignore, configs)</td></tr>
</table>

<h3>Exemplo:</h3>
<pre><code>git commit -m "feat: adicionar ServidorB com busca em artigosB.json"</code></pre>

<h2>✅ Checklist ao contribuir</h2>
<ul>
    <li>Código compila sem erros</li>
    <li>Segue o padrão de pastas e nomes</li>
    <li>Commit possui mensagem clara e prefixo semântico</li>
    <li>Evite subir arquivos <code>.class</code>, <code>.log</code>, <code>.swp</code>, etc.</li>
</ul>

<hr>
<p>Se tiver dúvidas, abra uma issue ou envie uma mensagem. Boa contribuição! 🚀</p>